package com.aldino.game;

import com.aldino.game.command.Command;
import com.aldino.game.command.CommandParser;
import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.aldino.game.model.Room;
import com.aldino.game.system.SaveManager;
import com.aldino.game.util.PrintHelp;
import com.aldino.game.util.PrintWelcome;
import com.aldino.game.util.ShowHealth;
import com.aldino.game.util.ShowInventory;
import com.aldino.game.util.CheckGameStatus;
import com.aldino.game.util.Look;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aldino.game.system.GameWorld;

public class BayangBorneoGame {

    private static final int MAX_SAVE_SLOT = 3;
    private final CommandParser parser;
    private Player player;
    private int activeSaveSlot = 1;
    private GameWorld gameWorld = new GameWorld(new HashMap<>());

    public BayangBorneoGame() {
        this.parser = new CommandParser();

        gameWorld.createRooms();
        chooseStartupProgress();

        PrintWelcome.display();
    }

    private void chooseStartupProgress() {
        List<Integer> availableSlots = SaveManager.getAvailableSlots(MAX_SAVE_SLOT);

        if (availableSlots.isEmpty()) {
            startNewGame(1);
            return;
        }

        System.out.println("Progress ditemukan di slot berikut:");
        for (Integer slot : availableSlots) {
            SaveManager.SaveSummary summary = SaveManager.loadSummary(slot);
            if (summary == null) {
                continue;
            }
            String roomName = (summary.getRoomName() == null || summary.getRoomName().isBlank())
                    ? gameWorld.getStartRoom().getName()
                    : summary.getRoomName();
            String status = summary.isFinished() ? " [TAMAT]" : "";
            System.out.println("Slot " + slot
                    + " | Player: " + summary.getPlayerName()
                    + " | HP: " + summary.getHealth()
                    + " | Room: " + roomName
                    + " | Inventory: " + summary.getInventoryCount() + " item"
                    + status);
        }
        System.out.println("Ketik nomor slot (1-" + MAX_SAVE_SLOT + ") untuk melanjutkan.");
        System.out.println("Ketik 0 untuk mulai game baru.");

        int selected = promptSlotSelection();
        if (selected == 0 || !loadGameFromSlot(selected)) {
            int newSlot = promptNewGameSlot();
            startNewGame(newSlot);
            return;
        }

        if (!loadGameFromSlot(selected)) {
            System.out.println("Slot tidak valid / rusak. Game baru dimulai di slot 1.");
            startNewGame(1);
        }
    }

    private boolean loadGameFromSlot(int slot) {
        SaveManager.LoadedGame loadedGame = SaveManager.load(slot);
        if (loadedGame == null) {
            return false;
        }

        activeSaveSlot = slot;
        this.player = loadedGame.getPlayer();
        Room loadedRoom = gameWorld.getRoomByName(loadedGame.getRoomName());
        gameWorld.setCurrentRoom((loadedRoom != null) ? loadedRoom : gameWorld.getStartRoom());
        player.setCurrentRoom(gameWorld.getCurrentRoom());
        removeCollectedItemsFromRooms();
        System.out.println("Progress dari slot " + slot + " berhasil dimuat.");
        return true;
    }

    private void startNewGame(int slot) {
        activeSaveSlot = slot;
        this.player = new Player("Aldino");
        gameWorld.resetToStartRoom();
        player.setCurrentRoom(gameWorld.getCurrentRoom());
        System.out.println("Memulai game baru di slot " + slot + ".");
    }

    private int promptSlotSelection() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Pilih slot: ");
            try {
                String line = reader.readLine();
                if (line == null) {
                    return 0;
                }
                int value = Integer.parseInt(line.trim());
                if (value == 0 || (value >= 1 && value <= MAX_SAVE_SLOT)) {
                    return value;
                }
            } catch (IOException | NumberFormatException ignored) {
            }
            System.out.println("Input tidak valid. Masukkan angka 0-" + MAX_SAVE_SLOT + ".");
        }
    }

    private int promptNewGameSlot() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Simpan progress baru di slot (1-" + MAX_SAVE_SLOT + "): ");
            try {
                String line = reader.readLine();
                if (line == null) {
                    return 1;
                }
                int value = Integer.parseInt(line.trim());
                if (value >= 1 && value <= MAX_SAVE_SLOT) {
                    return value;
                }
            } catch (IOException | NumberFormatException ignored) {
            }
            System.out.println("Input tidak valid. Masukkan angka 1-" + MAX_SAVE_SLOT + ".");
        }
    }

    private void removeCollectedItemsFromRooms() {
        for (Item item : player.getInventory()) {
            for (Room room : gameWorld.getRoomsByName().values()) {
                room.removeItem(item.getName());   // pakai method removeItem
            }
        }
    }

    public void play() {
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

            // Cek status game setelah setiap perintah
            if (!finished) {
                finished = CheckGameStatus.check(player);
            }
        }
    }

    private boolean processCommand(Command command) {
        switch (command.getType()) {
            case HELP:
                PrintHelp.displayHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case TAKE:
                takeItem(command);
                break;
            case LOOK:
                Look.display(gameWorld.getCurrentRoom());
                break;
            case INVENTORY:
                ShowInventory.display(player);
                break;
            case HEALTH:
                ShowHealth.display(player);
                break;
            case QUIT:
                return true;   // keluar dari game
            case UNKNOWN:
                System.out.println("Perintah tidak dikenali. Ketik 'help' untuk bantuan.");
                break;
            default:
                System.out.println("Perintah '" + command.getType() + "' belum diimplementasikan.");
        }
        return false;
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau mengambil apa? Contoh: take Sekop Berkarat");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = gameWorld.getCurrentRoom().removeItem(itemName);
        int heal = 15;

        if( item == null ) {
            System.out.println(itemName + " tidak ditemukan di " + gameWorld.getCurrentRoom().getName());
        } else if(!item.isCanBeTaken()) {
            System.out.println("Kamu tidak bisa mengambil " + item.getName() + ".");
            gameWorld.getCurrentRoom().addItem(item); // kembalikan ke ruangan
        } else {
            player.addItem(item);

            if(item.getName().equals("Kayu Ajaib")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Kayu Ajaib");
            } else if(item.getName().equals("Kitab Petunjuk")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Kitab Petunjuk");
            } else if(item.getName().equals("Cawan Kuno")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Cawan Kuno");
            } else if(item.getName().equals("Buah Biru")) {
                System.out.println("Kamu memakan buah biru dan merasa energi bertambah");
                System.out.println("Darah kamu akan bertambah sebanyak: " + heal);
                player.heal(heal);
                ShowHealth.display(player);
            }
            autoSave();
        }
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau pergi ke mana? Contoh: go east");
            return;
        }

        String direction = command.getSecondWord().toLowerCase();   // pastikan lowercase
        Room nextRoom = gameWorld.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("Tidak ada jalan ke arah '" + direction + "'.");
            System.out.println("Coba ketik 'look' untuk melihat arah yang tersedia.");
        } else {
            gameWorld.setCurrentRoom(nextRoom);              
            player.setCurrentRoom(gameWorld.getCurrentRoom());  
            
            System.out.println("\nKamu berjalan ke " + gameWorld.getCurrentRoom().getName() + "...");
            Look.display(gameWorld.getCurrentRoom());   // tampilkan deskripsi ruangan baru
            checkRoomHazards(); // cek bahaya di ruangan
            autoSave();
        }
    }

    private void checkRoomHazards() {
        int hutanLebatDamage = 20;
        int sungaiMististDamage = 10;
        int guaCahayaDamage = 15;
        int templarKunoDamage = 25;

        if(gameWorld.getCurrentRoom().getName().equals("Hutan Lebat")) {
            if(!player.hasItem("Kulit Hewan Besar")) {
                System.out.println("Kamu tidak memiliki item: Kulit Hewan Besar untuk melindungi diri dari duri");
                System.out.println("Darah kamu akan berkurang sebanyak: "  + hutanLebatDamage);
                player.takeDamage(hutanLebatDamage);
                ShowHealth.display(player);
            }
        } else if(gameWorld.getCurrentRoom().getName().equals("Sungai Mistis")) {
            if(!player.hasItem("Batang Pohon Besar")) {
                System.out.println("Kamu tidak memiliki item: Batang Pohon Besar untuk melindungi diri dari air yang deras");
                System.out.println("Darah kamu akan berkurang sebanyak: " + sungaiMististDamage);
                player.takeDamage(sungaiMististDamage);
                ShowHealth.display(player);
            }
        } else if(gameWorld.getCurrentRoom().getName().equals("Gua Cahaya")) {
            if(!player.hasItem("Pisau Batu")) {
                System.out.println("Kamu tidak memiliki item: Pisau Batu untuk bertahan dari binatang liar yang ada di gua");
                System.out.println("Darah kamu akan berkurang sebanyak: " + guaCahayaDamage);
                player.takeDamage(guaCahayaDamage);
                ShowHealth.display(player);
            }
        } else if(gameWorld.getCurrentRoom().getName().equals("Templar Kuno")) {
            if(!player.hasItem("Sepatu Kulit Hewan")) {
                System.out.println("Kamu tidak memiliki item: Sepatu Kulit Hewan akibatnya kamu menginjak batu tajam yang ada di sekitar templar");
                System.out.println("Darah kamu akan berkurang sebanyak: " + templarKunoDamage);
                player.takeDamage(templarKunoDamage);
                ShowHealth.display(player);
            }
        }
    }

    private void autoSave() {
        SaveManager.save(player, gameWorld.getCurrentRoom().getName(), activeSaveSlot);
    }

    public static void main(String[] args) {
        BayangBorneoGame game = new BayangBorneoGame();
        game.play();
    }
}

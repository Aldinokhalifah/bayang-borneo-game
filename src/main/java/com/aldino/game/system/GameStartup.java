package com.aldino.game.system;

import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.aldino.game.model.Room;
import com.aldino.game.util.SlotInput;
import java.util.List;

public class GameStartup {

    public static class Session {
        private final Player player;
        private final int activeSaveSlot;

        public Session(Player player, int activeSaveSlot) {
            this.player = player;
            this.activeSaveSlot = activeSaveSlot;
        }

        public Player getPlayer() {
            return player;
        }

        public int getActiveSaveSlot() {
            return activeSaveSlot;
        }
    }

    public Session chooseProgress(GameWorld gameWorld, int maxSaveSlot) {
        List<Integer> availableSlots = SaveManager.getAvailableSlots(maxSaveSlot);

        if (availableSlots.isEmpty()) {
            return startNewGame(gameWorld, 1);
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
        System.out.println("Ketik nomor slot (1-" + maxSaveSlot + ") untuk melanjutkan.");
        System.out.println("Ketik 0 untuk mulai game baru.");

        int selected = SlotInput.promptSlotSelection(maxSaveSlot);
        if (selected == 0) {
            int newSlot = SlotInput.promptNewGameSlot(maxSaveSlot);
            return startNewGame(gameWorld, newSlot);
        }

        Session loaded = loadFromSlot(gameWorld, selected);
        if (loaded != null) {
            return loaded;
        }

        int newSlot = SlotInput.promptNewGameSlot(maxSaveSlot);
        return startNewGame(gameWorld, newSlot);
    }

    public Session loadFromSlot(GameWorld gameWorld, int slot) {
        SaveManager.LoadedGame loadedGame = SaveManager.load(slot);
        if (loadedGame == null) {
            return null;
        }

        Player player = loadedGame.getPlayer();
        Room loadedRoom = gameWorld.getRoomByName(loadedGame.getRoomName());
        gameWorld.setCurrentRoom((loadedRoom != null) ? loadedRoom : gameWorld.getStartRoom());
        player.setCurrentRoom(gameWorld.getCurrentRoom());
        removeCollectedItemsFromRooms(player, gameWorld);
        System.out.println("Progress dari slot " + slot + " berhasil dimuat.");
        return new Session(player, slot);
    }

    public Session startNewGame(GameWorld gameWorld, int slot) {
        Player player = new Player("Aldino");
        gameWorld.resetToStartRoom();
        player.setCurrentRoom(gameWorld.getCurrentRoom());
        System.out.println("Memulai game baru di slot " + slot + ".");
        return new Session(player, slot);
    }

    private void removeCollectedItemsFromRooms(Player player, GameWorld gameWorld) {
        for (Item item : player.getInventory()) {
            for (Room room : gameWorld.getRoomsByName().values()) {
                room.removeItem(item.getName());
            }
        }
    }
}

package com.aldino.game.util;

import com.aldino.game.command.Command;
import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.aldino.game.model.Room;
import com.aldino.game.system.SaveManager;

public class TakeItem {

    private TakeItem() {
    }

    public static void execute(Command command, Player player, Room currentRoom, int activeSaveSlot) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau mengambil apa? Contoh: take Sekop Berkarat");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.removeItem(itemName);
        int heal = 15;

        if (item == null) {
            System.out.println(itemName + " tidak ditemukan di " + currentRoom.getName());
        } else if (!item.isCanBeTaken()) {
            System.out.println("Kamu tidak bisa mengambil " + item.getName() + ".");
            currentRoom.addItem(item);
        } else {
            player.addItem(item);

            if (item.getName().equals("Kayu Ajaib")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Kayu Ajaib");
            } else if (item.getName().equals("Kitab Petunjuk")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Kitab Petunjuk");
            } else if (item.getName().equals("Cawan Kuno")) {
                System.out.println("Selamat! kamu telah menemukan artefak kuno yang bernama: Cawan Kuno");
            } else if (item.getName().equals("Buah Biru")) {
                System.out.println("Kamu memakan buah biru dan merasa energi bertambah");
                System.out.println("Darah kamu akan bertambah sebanyak: " + heal);
                player.heal(heal);
                ShowHealth.display(player);
            }
            SaveManager.save(player, currentRoom.getName(), activeSaveSlot);
        }
    }
}

package com.aldino.game.util;

import com.aldino.game.command.Command;
import com.aldino.game.model.Player;
import com.aldino.game.model.Room;
import com.aldino.game.system.GameWorld;
import com.aldino.game.system.SaveManager;

public class GoRoom {

    private GoRoom() {
    }

    public static void execute(Command command, Player player, GameWorld gameWorld, int activeSaveSlot) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau pergi ke mana? Contoh: go east");
            return;
        }

        String direction = command.getSecondWord().toLowerCase();
        Room nextRoom = gameWorld.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("Tidak ada jalan ke arah '" + direction + "'.");
            System.out.println("Coba ketik 'look' untuk melihat arah yang tersedia.");
        } else {
            gameWorld.setCurrentRoom(nextRoom);
            player.setCurrentRoom(gameWorld.getCurrentRoom());

            System.out.println("\nKamu berjalan ke " + gameWorld.getCurrentRoom().getName() + "...");
            Look.display(gameWorld.getCurrentRoom());
            RoomHazards.check(gameWorld.getCurrentRoom(), player);
            SaveManager.save(player, gameWorld.getCurrentRoom().getName(), activeSaveSlot);
        }
    }
}

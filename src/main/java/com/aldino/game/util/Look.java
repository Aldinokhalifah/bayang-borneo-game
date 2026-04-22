package com.aldino.game.util;

import com.aldino.game.model.Room;

public class Look {
    public static void display(Room currentRoom) {
        System.out.println("\n=== " + currentRoom.getName() + " ===");
        System.out.println("Deskripsi: " + currentRoom.getDescription());
        System.out.println(currentRoom.getItemsDescription());
        System.out.println(currentRoom.getExitsDescription() + "\n");
    }
}

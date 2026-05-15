package com.aldino.game.util;

import com.aldino.game.model.Room;

public class Look {
    public static void display(Room currentRoom) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(ConsoleColor.CYAN + currentRoom.getName().toUpperCase() + ConsoleColor.RESET);
        System.out.println(currentRoom.getDescription());
        System.out.println("\n" + currentRoom.getItemsDescription());
        System.out.println(currentRoom.getExitsDescription());
        System.out.println("=".repeat(60) + "\n");
    }
}

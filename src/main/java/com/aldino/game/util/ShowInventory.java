package com.aldino.game.util;

import com.aldino.game.model.Player;

public class ShowInventory {
    public static void display(Player player) {
        System.out.println("\n=== INVENTORY KAMU ===");
        if (player.getInventory().isEmpty()) {
            System.out.println("Tas kamu masih kosong.");
        } else {
            player.getInventory().forEach(item -> 
                System.out.println("- " + item.getName() + " : " + item.getDescription())
            );
        }
        System.out.println("Total item: " + player.getInventory().size());
    }
}

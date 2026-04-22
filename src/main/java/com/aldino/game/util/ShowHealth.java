package com.aldino.game.util;

import com.aldino.game.model.Player;

public class ShowHealth {
    public static void display(Player player) {
        System.out.println("\n=== STATUS PLAYER ===");
        System.out.println(player.getHealthStatus());
    }
}

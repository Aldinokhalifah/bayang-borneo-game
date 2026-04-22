package com.aldino.game.util;

import com.aldino.game.model.Player;

public class CheckGameStatus {
    public static boolean check(Player player) {
         // Cek Game Over karena health habis
        if (!player.isAlive()) {
            PrintBadEnding.display();
            return true;
        }

        // Cek Win Condition (contoh: mengumpulkan 3 item penting)
        if (player.hasItem("Kitab Petunjuk") && 
            player.hasItem("Cawan Kuno") && 
            player.hasItem("Kayu Ajaib")) {
            PrintGoodEnding.display();
            return true;
        }

        return false;
    }
}

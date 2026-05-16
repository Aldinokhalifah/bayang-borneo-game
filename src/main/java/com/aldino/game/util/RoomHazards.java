package com.aldino.game.util;

import com.aldino.game.model.Player;
import com.aldino.game.model.Room;

public class RoomHazards {

    private RoomHazards() {
    }

    public static void check(Room currentRoom, Player player) {
        int hutanLebatDamage = 20;
        int sungaiMististDamage = 10;
        int guaCahayaDamage = 15;
        int templarKunoDamage = 25;

        if (currentRoom.getName().equals("Hutan Lebat")) {
            if (!player.hasItem("Kulit Hewan Besar")) {
                System.out.println("Kamu tidak memiliki item: Kulit Hewan Besar untuk melindungi diri dari duri");
                System.out.println("Darah kamu akan berkurang sebanyak: " + hutanLebatDamage);
                player.takeDamage(hutanLebatDamage);
                ShowHealth.display(player);
            }
        } else if (currentRoom.getName().equals("Sungai Mistis")) {
            if (!player.hasItem("Batang Pohon Besar")) {
                System.out.println("Kamu tidak memiliki item: Batang Pohon Besar untuk melindungi diri dari air yang deras");
                System.out.println("Darah kamu akan berkurang sebanyak: " + sungaiMististDamage);
                player.takeDamage(sungaiMististDamage);
                ShowHealth.display(player);
            }
        } else if (currentRoom.getName().equals("Gua Cahaya")) {
            if (!player.hasItem("Pisau Batu")) {
                System.out.println("Kamu tidak memiliki item: Pisau Batu untuk bertahan dari binatang liar yang ada di gua");
                System.out.println("Darah kamu akan berkurang sebanyak: " + guaCahayaDamage);
                player.takeDamage(guaCahayaDamage);
                ShowHealth.display(player);
            }
        } else if (currentRoom.getName().equals("Templar Kuno")) {
            if (!player.hasItem("Sepatu Kulit Hewan")) {
                System.out.println("Kamu tidak memiliki item: Sepatu Kulit Hewan akibatnya kamu menginjak batu tajam yang ada di sekitar templar");
                System.out.println("Darah kamu akan berkurang sebanyak: " + templarKunoDamage);
                player.takeDamage(templarKunoDamage);
                ShowHealth.display(player);
            }
        }
    }
}

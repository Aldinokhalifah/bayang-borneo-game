package com.aldino.game.util;

public class PrintHelp {
    public static void displayHelp() {
        System.out.println("\n=== BANTUAN PERINTAH ===");
        System.out.println("go [arah]    - Bergerak ke arah tertentu (north, south, dll)");
        System.out.println("look         - Melihat deskripsi ruangan saat ini");
        System.out.println("take [item]  - Mengambil item");
        System.out.println("inventory / inv    - Melihat barang di tas");
        System.out.println("health / hp  - Melihat sisa darah player");
        System.out.println("help         - Menampilkan bantuan ini");
        System.out.println("quit / exit         - Keluar dari game\n");
    }
}

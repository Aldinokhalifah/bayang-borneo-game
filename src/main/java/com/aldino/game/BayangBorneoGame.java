package com.aldino.game;

import com.aldino.game.command.Command;
import com.aldino.game.command.CommandParser;
import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.aldino.game.model.Room;

public class BayangBorneoGame {

    private final CommandParser parser;
    private final Player player;
    private Room currentRoom;        // ruangan tempat player sekarang
    private Room startRoom;          // ruangan awal game

    public BayangBorneoGame() {
        this.parser = new CommandParser();
        this.player = new Player("Aldino");

        createRooms();

        printWelcome();
    }

    private void printWelcome() {
        System.out.println("========================================");
        System.out.println("     BAYANG-BAYANG BORNEO");
        System.out.println("   Petualangan di Hutan Mistis Kalimantan");
        System.out.println("========================================");
        System.out.println("Kamu tersesat di tengah hutan yang gelap...");
        System.out.println("Ketik 'help' untuk melihat daftar perintah.\n");
    }

    private void createRooms() {
        // Membuat 6 ruangan baru dengan tema hutan Borneo
        Room halamanRumah = new Room("Halaman Rumah",
                "Kamu berada di halaman rumah tua yang tertutup rerumputan.\n"
                + "Di sekitar kamu ada pohon-pohon tinggi dan terdengar suara satwa liar.");

        Room hutanLebat = new Room("Hutan Lebat",
                "Kamu memasuki hutan yang sangat lebat dengan pepohonan raksasa.\n"
                + "Cahaya matahari hampir tidak menembus kanopi pohon yang rapat.");

        Room sungaiMistis = new Room("Sungai Mistis",
                "Sebuah sungai yang berarus deras mengalir di hadapanmu.\n"
                + "Kabut tebal menutup permukaan air, menciptakan suasana yang misterius.");

        Room guaCahaya = new Room("Gua Cahaya",
                "Kamu menemukan sebuah gua dengan cahaya berwarna biru yang aneh.\n"
                + "Terdengar suara aneh dari dalam gua yang menggema.");

        Room templarKuno = new Room("Templar Kuno",
                "Reruntuhan sebuah tempat ibadah kuno dengan ukiran-ukiran misterius.\n"
                + "Di tengahnya ada altar batu yang tertutup lumut dan tanaman merambat.");

        Room bukitTinggi = new Room("Bukit Tinggi",
                "Kamu mencapai puncak sebuah bukit dengan pemandangan hutan yang luas.\n"
                + "Di kejauhan terlihat puncak gunung yang tertutup awan tebal.");

        // Menghubungkan ruangan-ruangan dengan arah
        halamanRumah.addExit("east", hutanLebat);
        hutanLebat.addExit("west", halamanRumah);
        hutanLebat.addExit("south", sungaiMistis);
        sungaiMistis.addExit("north", hutanLebat);
        sungaiMistis.addExit("east", guaCahaya);
        guaCahaya.addExit("west", sungaiMistis);
        guaCahaya.addExit("north", templarKuno);
        templarKuno.addExit("south", guaCahaya);
        templarKuno.addExit("east", bukitTinggi);
        bukitTinggi.addExit("west", templarKuno);

        // Menambahkan item
        halamanRumah.addItem(new Item("Sekop Berkarat", "sekop yang digunakan untuk mengubur harta karun yang tertinggal"));
        halamanRumah.addItem(new Item("Kunci Rumah", "kunci untuk membuka rumah tua"));
        halamanRumah.addItem(new Item("Kendaraan Roda 3", "kendaraan yang dipakai oleh penghuni rumah", false));
        hutanLebat.addItem(new Item("Kayu Ajaib", "kayu yang dapat berubah bentuk sesuai dengan keinginan pemakainya"));
        hutanLebat.addItem(new Item("Kulit Hewan Besar", "dapat digunakan sebagai baju pelindung"));
        hutanLebat.addItem(new Item("Pohon Kramat", "pohon suci yang sudah ada sekitar 1000 tahun", false));
        sungaiMistis.addItem(new Item("Batang Pohon Besar", "dapat digunakan sebagai sekoci untuk melewati sungai"));
        sungaiMistis.addItem(new Item("Pancingan & Kail", "dapat digunakan untuk mencari ikan di sungai"));
        sungaiMistis.addItem(new Item("Tulang Hewan Purba", "tulang fosil dari hewan purba", false));
        guaCahaya.addItem(new Item("Cawan Kuno", "cawan yang berasal dari suku kuno"));
        guaCahaya.addItem(new Item("Pisau Batu", "pisau yang terbuat dari batu yang diukir"));
        guaCahaya.addItem(new Item("Tulang Belulang", "tulang dari spesies yang tidak diketahui", false));
        templarKuno.addItem(new Item("Kitab Petunjuk", "kitab yang menunjukkan peta menuju suatu tempat tersembunyi"));
        templarKuno.addItem(new Item("Lukisan Kulit Hewan", "lukisan yang dilukis di atas kulit hewan"));
        templarKuno.addItem(new Item("Furnitur Batu", "tempat dilaksanakannya ibadah", false));
        bukitTinggi.addItem(new Item("Tali Tumbuhan", "dapat digunakan untuk mengikat sesuatu"));
        bukitTinggi.addItem(new Item("Buah Biru", "buah yang dapat dikonsumsi untuk menambahkan tenaga"));

        startRoom = halamanRumah;
        currentRoom = startRoom;
        player.setCurrentRoom(currentRoom);
    }

    public void play() {
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
    }

    private boolean processCommand(Command command) {
        switch (command.getType()) {
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case TAKE:
                takeItem(command);
                break;
            case LOOK:
                look();
                break;
            case INVENTORY:
                showInventory();
                break;
            case QUIT:
                return true;   // keluar dari game
            case UNKNOWN:
                System.out.println("Perintah tidak dikenali. Ketik 'help' untuk bantuan.");
                break;
            default:
                System.out.println("Perintah '" + command.getType() + "' belum diimplementasikan.");
        }
        return false;
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau mengambil apa? Contoh: take Sekop Berkarat");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.removeItem(itemName);

        if( item == null ) {
            System.out.println(itemName + " tidak ditemukan di " + currentRoom.getName());
        } else if(!item.isCanBeTaken()) {
            System.out.println("Kamu tidak bisa mengambil " + item.getName() + ".");
            currentRoom.addItem(item); // kembalikan ke ruangan
        } else {
            player.addItem(item);
        }
    }

    private void showInventory() {
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

    private void look() {
        System.out.println("\n=== " + currentRoom.getName() + " ===");
        System.out.println("Deskripsi: " + currentRoom.getDescription());
        System.out.println(currentRoom.getItemsDescription());
        System.out.println(currentRoom.getExitsDescription() + "\n");
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Mau pergi ke mana? Contoh: go east");
            return;
        }

        String direction = command.getSecondWord().toLowerCase();   // pastikan lowercase
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Tidak ada jalan ke arah '" + direction + "'.");
            System.out.println("Coba ketik 'look' untuk melihat arah yang tersedia.");
        } else {
            currentRoom = nextRoom;              
            player.setCurrentRoom(currentRoom);  
            
            System.out.println("\nKamu berjalan ke " + currentRoom.getName() + "...");
            look();   // tampilkan deskripsi ruangan baru
        }
    }

    private void printHelp() {
        System.out.println("\n=== BANTUAN PERINTAH ===");
        System.out.println("go [arah]    - Bergerak ke arah tertentu (north, south, dll)");
        System.out.println("look         - Melihat deskripsi ruangan saat ini");
        System.out.println("take [item]  - Mengambil item");
        System.out.println("inventory    - Melihat barang di tas");
        System.out.println("help         - Menampilkan bantuan ini");
        System.out.println("quit         - Keluar dari game");
    }

    public static void main(String[] args) {
        BayangBorneoGame game = new BayangBorneoGame();
        game.play();
    }
}

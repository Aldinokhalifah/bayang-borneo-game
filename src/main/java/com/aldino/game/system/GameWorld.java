package com.aldino.game.system;

import java.util.HashMap;
import java.util.Map;

import com.aldino.game.model.Item;
import com.aldino.game.model.Room;

public class GameWorld {
    private Room currentRoom;        // ruangan tempat player sekarang
    private Room startRoom;          // ruangan awal game
    private Map<String, Room> roomsByName;

    public GameWorld() {   // tanpa parameter
        this.roomsByName = new HashMap<>();
        createRooms();
    }

    public void createRooms() {
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
        templarKuno.addItem(new Item("Sepatu Kulit Hewan", "sepatu yang terbuat dari kulit hewan"));
        templarKuno.addItem(new Item("Furnitur Batu", "tempat dilaksanakannya ibadah", false));
        bukitTinggi.addItem(new Item("Tali Tumbuhan", "dapat digunakan untuk mengikat sesuatu"));
        bukitTinggi.addItem(new Item("Buah Biru", "buah yang dapat dikonsumsi untuk menambahkan tenaga"));

        roomsByName.put(halamanRumah.getName(), halamanRumah);
        roomsByName.put(hutanLebat.getName(), hutanLebat);
        roomsByName.put(sungaiMistis.getName(), sungaiMistis);
        roomsByName.put(guaCahaya.getName(), guaCahaya);
        roomsByName.put(templarKuno.getName(), templarKuno);
        roomsByName.put(bukitTinggi.getName(), bukitTinggi);

        startRoom = hutanLebat;
        currentRoom = startRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getStartRoom() {
        return startRoom;
    }

    public void setStartRoom(Room startRoom) {
        this.startRoom = startRoom;
    }

    public Map<String, Room> getRoomsByName() {
        return roomsByName;
    }

    public void setRoomsByName(Map<String, Room> roomsByName) {
        this.roomsByName = roomsByName;
    }

    public Room getRoomByName(String name) {
        return roomsByName.get(name);
    }
    
    public void resetToStartRoom() {
        currentRoom = startRoom;
    }
}

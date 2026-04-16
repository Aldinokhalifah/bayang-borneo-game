package com.aldino.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private final String name;
    private final String description;

    @Setter
    private boolean visited = false;

    private final Map<String, Room> exits = new HashMap<>();
    private final List<Item> items = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // menambahkan arah keluar(north, south, east, west)
    public void addExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }

    // mendapatkan arah keluar
    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    // menambahkan item
    public void addItem(Item item) {
        items.add(item);
    }

    // menghapus item
    public Item removeItem(String itemName) {
        return items.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .map(item -> {
                    items.remove(item);
                    return item;
                })
                .orElse(null);
    }

    public String getItemsDescription() {
        if (items.isEmpty()) {
            return "Tidak ada item di sini.";
        }
        List<String> itemNames = items.stream()
                                    .map(Item::getName)
                                    .toList();
        return "Item yang tersedia: " + String.join(", ", itemNames);
    }

    public String getExitsDescription() {
        if (exits.isEmpty()) return "Tidak ada jalan keluar.";
        return "Jalan keluar: " + String.join(", ", exits.keySet());
    }
}

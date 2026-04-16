package com.aldino.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    private final String name;
    private int health = 100;
    
    @Setter
    private Room currentRoom;

    private final List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Kamu mengambil: " + item.getName());
    }

    public boolean hasItem(String itemName) {
        return inventory.stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(itemName));
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getStatus() {
        return "❤️  Health: " + health + " | Inventory: " + 
            (inventory.isEmpty() ? "kosong" : inventory.size() + " item");
    }
}
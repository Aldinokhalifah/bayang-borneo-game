package com.aldino.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    private final String name;
    private int health = 100;
    private int maxHealth = 100;
    
    @Setter
    private Room currentRoom;

    private final List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
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

    public String getHealthBar() {
        return "❤️".repeat(health / 10) + " (" + health + "/" + maxHealth + ")";
    }

    public String getHealthStatus() {
        return "Sisa darah: " + health + "/" + maxHealth;
    }
}
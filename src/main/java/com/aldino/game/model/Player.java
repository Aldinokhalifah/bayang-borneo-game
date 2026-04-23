package com.aldino.game.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    private String name;
    private int health = 100;
    private int maxHealth = 100;
    
    private Room currentRoom;

    private List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public Player() {
        
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public void restoreState(String name, int health, int maxHealth, List<Item> inventory) {
        this.name = (name == null || name.isBlank()) ? "Player" : name;
        this.maxHealth = Math.max(1, maxHealth);
        this.health = Math.max(0, Math.min(this.maxHealth, health));
        this.inventory = (inventory == null) ? new ArrayList<>() : new ArrayList<>(inventory);
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
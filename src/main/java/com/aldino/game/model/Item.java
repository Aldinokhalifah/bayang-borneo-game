package com.aldino.game.model;

public class Item {

    private String name;
    private String description;
    private boolean canBeTaken;

    public Item(String name, String description, boolean canBeTaken) {
        this.name = name;
        this.description = description;
        this.canBeTaken = canBeTaken;
    }

    public Item(String name, String description) {
        this(name, description, true);
    }

    public Item() {
        
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCanBeTaken() {
        return canBeTaken;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
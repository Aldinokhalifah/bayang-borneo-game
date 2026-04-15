package com.aldino.game.model;

public class Item {

    private final String name;
    private final String description;
    private final boolean canBeTaken;

    public Item(String name, String description, boolean canBeTaken) {
        this.name = name;
        this.description = description;
        this.canBeTaken = canBeTaken;
    }

    public Item(String name, String description) {
        this(name, description, true);
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
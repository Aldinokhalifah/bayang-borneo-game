package com.aldino.game.system;

import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveManager {

    private static final Gson gson = new Gson();
    private static final String SAVE_DIRECTORY = "src/main/java/com/aldino/game/progress";

    public static void save(Player player, String currentRoomName, int slot) {
        SaveData data = new SaveData();
        data.name = player.getName();
        data.health = player.getHealth();
        data.maxHealth = player.getMaxHealth();
        data.inventory = new ArrayList<>(player.getInventory());
        data.currentRoomName = currentRoomName;

        File saveFile = getSaveFile(slot);
        ensureSaveDirectoryExists();

        try (FileWriter writer = new FileWriter(saveFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoadedGame load(int slot) {
        SaveData data = readSaveData(slot);
        if (data == null) {
            return null;
        }

        Player player = new Player();
        player.restoreState(data.name, data.health, data.maxHealth, data.inventory);
        return new LoadedGame(player, data.currentRoomName);
    }

    public static List<Integer> getAvailableSlots(int maxSlot) {
        if (maxSlot <= 0) {
            return Collections.emptyList();
        }

        List<Integer> slots = new ArrayList<>();
        for (int slot = 1; slot <= maxSlot; slot++) {
            if (hasSave(slot)) {
                slots.add(slot);
            }
        }
        return slots;
    }

    public static boolean hasSave(int slot) {
        return readSaveData(slot) != null;
    }

    public static SaveSummary loadSummary(int slot) {
        SaveData data = readSaveData(slot);
        if (data == null) {
            return null;
        }

        int inventoryCount = (data.inventory == null) ? 0 : data.inventory.size();
        boolean finished = isWinningInventory(data.inventory);
        return new SaveSummary(data.name, data.health, data.currentRoomName, inventoryCount, finished);
    }

    private static boolean isWinningInventory(List<Item> inventory) {
        if (inventory == null || inventory.isEmpty()) {
            return false;
        }

        boolean hasKitab = false;
        boolean hasCawan = false;
        boolean hasKayu = false;

        for (Item item : inventory) {
            if (item == null || item.getName() == null) {
                continue;
            }

            String name = item.getName();
            if ("Kitab Petunjuk".equalsIgnoreCase(name)) {
                hasKitab = true;
            } else if ("Cawan Kuno".equalsIgnoreCase(name)) {
                hasCawan = true;
            } else if ("Kayu Ajaib".equalsIgnoreCase(name)) {
                hasKayu = true;
            }
        }

        return hasKitab && hasCawan && hasKayu;
    }

    private static SaveData readSaveData(int slot) {
        File saveFile = getSaveFile(slot);
        if (!saveFile.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(saveFile)) {
            SaveData data = gson.fromJson(reader, SaveData.class);
            return (data == null) ? null : data;
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Save slot " + slot + " rusak atau tidak bisa dibaca.");
            return null;
        }
    }

    private static File getSaveFile(int slot) {
        return new File(SAVE_DIRECTORY, "save" + slot + ".json");
    }

    private static void ensureSaveDirectoryExists() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static final class LoadedGame {
        private final Player player;
        private final String roomName;

        public LoadedGame(Player player, String roomName) {
            this.player = player;
            this.roomName = roomName;
        }

        public Player getPlayer() {
            return player;
        }

        public String getRoomName() {
            return roomName;
        }
    }

    public static final class SaveSummary {
        private final String playerName;
        private final int health;
        private final String roomName;
        private final int inventoryCount;
        private final boolean finished;

        public SaveSummary(String playerName, int health, String roomName, int inventoryCount, boolean finished) {
            this.playerName = playerName;
            this.health = health;
            this.roomName = roomName;
            this.inventoryCount = inventoryCount;
            this.finished = finished;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getHealth() {
            return health;
        }

        public String getRoomName() {
            return roomName;
        }

        public int getInventoryCount() {
            return inventoryCount;
        }

        public boolean isFinished() {
            return finished;
        }
    }

    private static final class SaveData {
        private String name;
        private int health;
        private int maxHealth;
        private List<Item> inventory;
        private String currentRoomName;
    }
}

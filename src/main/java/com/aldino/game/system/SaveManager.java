package com.aldino.game.system;

import com.aldino.game.model.Item;
import com.aldino.game.model.Player;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {

    private static final Gson gson = new Gson();

    public static void save(Player player, String currentRoomName, int slot) {
        SaveData data = new SaveData();
        data.name = player.getName();
        data.health = player.getHealth();
        data.maxHealth = player.getMaxHealth();
        data.inventory = new ArrayList<>(player.getInventory());
        data.currentRoomName = currentRoomName;

        try (FileWriter writer = new FileWriter("save" + slot + ".json")) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoadedGame load(int slot) {
        File saveFile = new File("save" + slot + ".json");
        if (!saveFile.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader("save" + slot + ".json")) {
            SaveData data = gson.fromJson(reader, SaveData.class);
            if (data == null) {
                return null;
            }

            Player player = new Player();
            player.restoreState(data.name, data.health, data.maxHealth, data.inventory);
            return new LoadedGame(player, data.currentRoomName);
        } catch (IOException | JsonSyntaxException e) {
            System.out.println("Save rusak atau tidak bisa dibaca, mulai game baru.");
            return null;
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

    private static final class SaveData {
        private String name;
        private int health;
        private int maxHealth;
        private List<Item> inventory;
        private String currentRoomName;
    }
}

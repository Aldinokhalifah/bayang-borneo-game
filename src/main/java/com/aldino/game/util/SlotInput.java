package com.aldino.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SlotInput {

    private SlotInput() {
    }

    public static int promptSlotSelection(int maxSaveSlot) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Pilih slot: ");
            try {
                String line = reader.readLine();
                if (line == null) {
                    return 0;
                }
                int value = Integer.parseInt(line.trim());
                if (value == 0 || (value >= 1 && value <= maxSaveSlot)) {
                    return value;
                }
            } catch (IOException | NumberFormatException ignored) {
            }
            System.out.println("Input tidak valid. Masukkan angka 0-" + maxSaveSlot + ".");
        }
    }

    public static int promptNewGameSlot(int maxSaveSlot) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Simpan progress baru di slot (1-" + maxSaveSlot + "): ");
            try {
                String line = reader.readLine();
                if (line == null) {
                    return 1;
                }
                int value = Integer.parseInt(line.trim());
                if (value >= 1 && value <= maxSaveSlot) {
                    return value;
                }
            } catch (IOException | NumberFormatException ignored) {
            }
            System.out.println("Input tidak valid. Masukkan angka 1-" + maxSaveSlot + ".");
        }
    }
}

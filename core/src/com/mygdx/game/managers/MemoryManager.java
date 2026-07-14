package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("space-cleaner-saves");

    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return preferences.getBoolean("isSoundOn", true);
    }

    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }

    public static void saveTableOfRecords(ArrayList<Integer> table) {
        ArrayList<Integer> safeTable = table == null ? new ArrayList<>() : new ArrayList<>(table);
        while (safeTable.size() > 5) {
            safeTable.remove(safeTable.size() - 1);
        }
        preferences.putString("recordTable", new Json().toJson(safeTable));
        preferences.flush();
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Integer> loadRecordsTable() {
        if (!preferences.contains("recordTable")) {
            return new ArrayList<>();
        }
        ArrayList<Integer> table = new Json().fromJson(ArrayList.class, preferences.getString("recordTable"));
        return table == null ? new ArrayList<>() : table;
    }

    private MemoryManager() {
    }
}

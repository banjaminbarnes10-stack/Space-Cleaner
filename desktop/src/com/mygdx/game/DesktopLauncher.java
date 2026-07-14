package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Space Cleaner");
        config.setWindowedMode(GameSettings.SCREEN_WIDTH / 2, GameSettings.SCREEN_HEIGHT / 2);
        config.setResizable(false);
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}

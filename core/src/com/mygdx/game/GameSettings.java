package com.mygdx.game;

public class GameSettings {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;
    public static final float SCALE = 0.02f;

    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 122;
    public static final float SHIP_VELOCITY = 620f;

    public static final float TRASH_VELOCITY = 250f;
    public static final double STARTING_TRASH_APPEARANCE_COOL_DOWN = 1800.0;
    public static final double MIN_TRASH_APPEARANCE_COOL_DOWN = 520.0;
    public static final int TRASH_WIDTH = 132;
    public static final int TRASH_HEIGHT = 98;
    public static final float TRASH_ROTATION = 2.4f;

    public static final float BULLET_VELOCITY = 900f;
    public static final int SHOOTING_COOL_DOWN = 380;
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 48;

    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;

    private GameSettings() {
    }
}

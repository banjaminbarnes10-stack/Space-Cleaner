package com.mygdx.game.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {
    private static final int paddingHorizontal = 30;
    private static final Random RANDOM = new Random();
    private int livesLeft;
    private float torque;
    private boolean destroyedByBullet;

    public TrashObject(int width, int height, String texturePath, World world) {
        super(texturePath, randomX(width), GameSettings.SCREEN_HEIGHT + height / 2,
                width, height, world, GameSettings.TRASH_BIT);
        body.setFixedRotation(false);
        body.setLinearVelocity(new Vector2(0f, -GameSettings.TRASH_VELOCITY * GameSettings.SCALE));
        body.setAngularVelocity(MathUtils.random(-GameSettings.TRASH_ROTATION, GameSettings.TRASH_ROTATION));
        torque = body.getInertia() * MathUtils.degreesToRadians * 90f;
        livesLeft = 1;
    }

    private static int randomX(int width) {
        int min = width / 2 + paddingHorizontal;
        int max = GameSettings.SCREEN_WIDTH - width / 2 - paddingHorizontal;
        return min + RANDOM.nextInt(Math.max(1, max - min + 1));
    }

    public boolean isInFrame() {
        return getY() + height / 2f > 0f;
    }

    @Override
    public void hit() {
        livesLeft = 0;
    }

    public void hitByBullet() {
        destroyedByBullet = true;
        hit();
    }

    public void hitByShip() {
        destroyedByBullet = false;
        hit();
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean wasDestroyedByBullet() {
        return destroyedByBullet;
    }

    public float getTorque() {
        return torque;
    }
}

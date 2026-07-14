package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject {
    boolean wasHit;

    public BulletObject(String texturePath, int x, int y, int width, int height, World world) {
        super(texturePath, x, y, width, height, world, GameSettings.BULLET_BIT);
        body.setBullet(true);
        body.setLinearVelocity(new Vector2(0f, GameSettings.BULLET_VELOCITY * GameSettings.SCALE));
        wasHit = false;
    }

    public boolean hasToBeDestroyed() {
        return wasHit || getY() - height / 2f > GameSettings.SCREEN_HEIGHT;
    }

    @Override
    public void hit() {
        wasHit = true;
    }
}

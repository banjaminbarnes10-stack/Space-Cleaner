package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject {
    private long lastShotTime;
    private int livesLeft;

    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, world, GameSettings.SHIP_BIT);
        body.setLinearDamping(9f);
        lastShotTime = TimeUtils.millis();
        livesLeft = 3;
    }

    @Override
    public void hit() {
        if (livesLeft > 0) {
            livesLeft--;
        }
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean needToShoot() {
        long now = TimeUtils.millis();
        if (now - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = now;
            return true;
        }
        return false;
    }

    private void putInFrame() {
        float minX = width / 2f;
        float maxX = GameSettings.SCREEN_WIDTH - width / 2f;
        float minY = height / 2f;
        float maxY = GameSettings.SCREEN_HEIGHT / 2f - height / 2f;

        float clampedX = MathUtils.clamp(getX(), minX, maxX);
        float clampedY = MathUtils.clamp(getY(), minY, maxY);

        if (clampedX != getX() || clampedY != getY()) {
            body.setTransform(clampedX * GameSettings.SCALE, clampedY * GameSettings.SCALE, 0f);
            body.setLinearVelocity(0f, 0f);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void update() {
        super.update();
        putInFrame();
        super.update();
    }

    public void move(Vector3 touchScreen) {
        float targetX = MathUtils.clamp(touchScreen.x, width / 2f, GameSettings.SCREEN_WIDTH - width / 2f);
        float targetY = MathUtils.clamp(touchScreen.y, height / 2f,
                GameSettings.SCREEN_HEIGHT / 2f - height / 2f);

        Vector2 current = body.getPosition();
        Vector2 target = new Vector2(targetX * GameSettings.SCALE, targetY * GameSettings.SCALE);
        Vector2 direction = target.sub(current);
        float distance = direction.len();

        if (distance < 0.08f) {
            body.setLinearVelocity(0f, 0f);
            return;
        }

        float speed = GameSettings.SHIP_VELOCITY * GameSettings.SCALE;
        float adjustedSpeed = Math.min(speed, distance * 9f);
        body.setLinearVelocity(direction.nor().scl(adjustedSpeed));
    }

    public int getLivesLeft() {
        return livesLeft;
    }
}

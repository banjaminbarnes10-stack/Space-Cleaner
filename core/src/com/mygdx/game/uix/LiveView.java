package com.mygdx.game.uix;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;

public class LiveView extends View {
    private int leftLives;
    private final int livePadding;

    public LiveView(float x, float y, int lives) {
        super(x, y);
        setTexture(new Texture(GameResources.LIVE_IMG_PATH));
        setWidth(getTexture().getWidth());
        setHeight(getTexture().getHeight());
        leftLives = lives;
        livePadding = 8;
    }

    public int getLeftLives() {
        return leftLives;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = Math.max(0, leftLives);
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < leftLives; i++) {
            batch.draw(getTexture(), getX() + i * (getWidth() + livePadding), getY(), getWidth(), getHeight());
        }
    }
}

package com.mygdx.game.uix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class MovingBackgroundView extends View {
    private float texture1Y;
    private float texture2Y;
    private float speed = 90f;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y = 0f;
        texture2Y = GameSettings.SCREEN_HEIGHT;
        setTexture(new Texture(pathToTexture));
    }

    public void move() {
        move(Gdx.graphics.getDeltaTime());
    }

    public void move(float delta) {
        float shift = speed * Math.min(delta, 0.05f);
        texture1Y -= shift;
        texture2Y -= shift;

        if (texture1Y <= -GameSettings.SCREEN_HEIGHT) {
            texture1Y = texture2Y + GameSettings.SCREEN_HEIGHT;
        }
        if (texture2Y <= -GameSettings.SCREEN_HEIGHT) {
            texture2Y = texture1Y + GameSettings.SCREEN_HEIGHT;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), 0, texture1Y, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(getTexture(), 0, texture2Y, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
    }
}

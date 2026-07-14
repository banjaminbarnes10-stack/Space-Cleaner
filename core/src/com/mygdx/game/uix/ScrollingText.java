package com.mygdx.game.uix;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ScrollingText {
    private final List<String> lines;
    private final List<Float> yPositions;
    private final BitmapFont font;
    private final float speed;
    private final float screenHeight;

    public ScrollingText(BitmapFont font, float screenHeight, float speed) {
        this.font = font;
        this.screenHeight = screenHeight;
        this.speed = speed;
        lines = new ArrayList<>();
        yPositions = new ArrayList<>();

        lines.add("Управляй кораблём касанием");
        lines.add("Лазер стреляет автоматически");
        lines.add("Береги три жизни");

        for (int i = 0; i < lines.size(); i++) {
            yPositions.add(230f - i * 42f);
        }
    }

    public void update(float delta) {
        for (int i = 0; i < yPositions.size(); i++) {
            float y = yPositions.get(i) + speed * delta;
            if (y > screenHeight / 3f) {
                y = 130f;
            }
            yPositions.set(i, y);
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < lines.size(); i++) {
            font.draw(batch, lines.get(i), 150f, yPositions.get(i));
        }
    }
}

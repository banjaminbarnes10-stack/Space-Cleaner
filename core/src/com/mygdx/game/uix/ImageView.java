package com.mygdx.game.uix;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends View {
    public ImageView(float x, float y, String imagePath) {
        super(x, y);
        setTexture(new Texture(imagePath));
        setWidth(getTexture().getWidth());
        setHeight(getTexture().getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getWidth(), getHeight());
    }
}

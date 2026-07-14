package com.mygdx.game.uix;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView extends View {
    private final Texture texture;
    private final BitmapFont bitmapFont;
    private String text;
    private float textX;
    private float textY;

    public ButtonView(float x, float y, float width, float height, BitmapFont font, String texturePath, String text) {
        super(x, y, width, height);
        this.bitmapFont = font;
        this.texture = new Texture(texturePath);
        setText(text);
    }

    public ButtonView(float x, float y, float width, float height, String texturePath) {
        super(x, y, width, height);
        this.bitmapFont = null;
        this.texture = new Texture(texturePath);
        this.text = "";
    }

    private void updateTextPosition() {
        if (bitmapFont == null) {
            return;
        }
        GlyphLayout layout = new GlyphLayout(bitmapFont, text);
        textX = getX() + (getWidth() - layout.width) / 2f;
        textY = getY() + (getHeight() + layout.height) / 2f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if (bitmapFont != null && text != null) {
            updateTextPosition();
            bitmapFont.draw(batch, text, textX, textY);
        }
    }

    @Override
    public boolean isHit(float tx, float ty) {
        return super.isHit(tx, ty);
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
        updateTextPosition();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

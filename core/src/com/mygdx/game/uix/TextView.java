package com.mygdx.game.uix;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View {
    private BitmapFont font;
    private String text;

    public TextView(BitmapFont font, float x, float y) {
        this(font, x, y, "");
    }

    public TextView(BitmapFont font, float x, float y, String text) {
        super(x, y);
        this.font = font;
        setText(text);
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
        GlyphLayout layout = new GlyphLayout(font, this.text);
        setWidth(layout.width);
        setHeight(layout.height);
    }

    public String getText() {
        return text;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
        setText(text);
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, getX(), getY());
    }

    @Override
    public void dispose() {
    }
}

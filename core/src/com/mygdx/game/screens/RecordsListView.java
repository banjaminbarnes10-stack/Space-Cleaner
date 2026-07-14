package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.GameSettings;
import com.mygdx.game.uix.TextView;

import java.util.ArrayList;

public class RecordsListView extends TextView {
    public RecordsListView(BitmapFont font, float y) {
        super(font, 0, y, "");
    }

    public void setRecords(ArrayList<Integer> recordsList) {
        ArrayList<Integer> safeList = recordsList == null ? new ArrayList<>() : recordsList;
        StringBuilder text = new StringBuilder();
        int rows = Math.min(safeList.size(), 5);
        if (rows == 0) {
            text.append("Рекордов пока нет");
        } else {
            for (int i = 0; i < rows; i++) {
                text.append(i + 1).append(".  ").append(safeList.get(i));
                if (i < rows - 1) {
                    text.append('\n');
                }
            }
        }
        setText(text.toString());
        GlyphLayout layout = new GlyphLayout(getFont(), getText());
        setX((GameSettings.SCREEN_WIDTH - layout.width) / 2f);
    }
}

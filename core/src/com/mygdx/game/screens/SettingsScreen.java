package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.uix.ButtonView;
import com.mygdx.game.uix.ImageView;
import com.mygdx.game.uix.TextView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    private final MyGdxGame myGdxGame;
    private final ImageView backgroundView;
    private final ImageView blackoutImageView;
    private final ButtonView returnButton;
    private final ButtonView musicSettingView;
    private final ButtonView soundSettingView;
    private final ButtonView clearSettingView;
    private final TextView titleTextView;
    private boolean isSoundOn;
    private boolean isMusicOn;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new ImageView(0, 0, GameResources.BACKGROUND_IMG_PATH);
        backgroundView.setWidth(GameSettings.SCREEN_WIDTH);
        backgroundView.setHeight(GameSettings.SCREEN_HEIGHT);
        titleTextView = new TextView(myGdxGame.getLargeWhiteFont(), 235, 1000, "НАСТРОЙКИ");
        blackoutImageView = new ImageView(85, 390, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        musicSettingView = new ButtonView(140, 760, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "");
        soundSettingView = new ButtonView(140, 660, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "");
        clearSettingView = new ButtonView(140, 560, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "ОЧИСТИТЬ РЕКОРДЫ");
        returnButton = new ButtonView(280, 440, 160, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_SHORT_BG_IMG_PATH, "НАЗАД");
        refreshFlags();
    }

    private void refreshFlags() {
        isMusicOn = MemoryManager.loadIsMusicOn();
        isSoundOn = MemoryManager.loadIsSoundOn();
        clearSettingView.setText("ОЧИСТИТЬ РЕКОРДЫ");
        draw();
    }

    @Override
    public void show() {
        refreshFlags();
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.getCamera().update();
        myGdxGame.getBatch().setProjectionMatrix(myGdxGame.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.getBatch().begin();
        backgroundView.draw(myGdxGame.getBatch());
        blackoutImageView.draw(myGdxGame.getBatch());
        titleTextView.draw(myGdxGame.getBatch());
        musicSettingView.draw(myGdxGame.getBatch());
        soundSettingView.draw(myGdxGame.getBatch());
        clearSettingView.draw(myGdxGame.getBatch());
        returnButton.draw(myGdxGame.getBatch());
        myGdxGame.getBatch().end();
    }

    private void draw() {
        musicSettingView.setText("МУЗЫКА: " + translateStateToText(isMusicOn));
        soundSettingView.setText("ЗВУК: " + translateStateToText(isSoundOn));
    }

    void handleInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }
        myGdxGame.touch = myGdxGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.setScreen(myGdxGame.getMenu());
        } else if (musicSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
            isMusicOn = MemoryManager.loadIsMusicOn();
            musicSettingView.setText("МУЗЫКА: " + translateStateToText(isMusicOn));
            myGdxGame.getAudioManager().updateMusicFlag();
        } else if (soundSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
            isSoundOn = MemoryManager.loadIsSoundOn();
            soundSettingView.setText("ЗВУК: " + translateStateToText(isSoundOn));
            myGdxGame.getAudioManager().updateSoundFlag();
        } else if (clearSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            MemoryManager.saveTableOfRecords(new ArrayList<>());
            clearSettingView.setText("РЕКОРДЫ ОЧИЩЕНЫ");
        }
    }

    public void updateMusicFlag() {
        MemoryManager.saveMusicSettings(isMusicOn);
        myGdxGame.getAudioManager().updateMusicFlag();
    }

    private String translateStateToText(boolean state) {
        return state ? "ВКЛ" : "ВЫКЛ";
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        blackoutImageView.dispose();
        musicSettingView.dispose();
        soundSettingView.dispose();
        clearSettingView.dispose();
        returnButton.dispose();
    }
}

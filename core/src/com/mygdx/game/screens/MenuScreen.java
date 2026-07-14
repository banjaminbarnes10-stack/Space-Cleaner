package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.uix.ButtonView;
import com.mygdx.game.uix.ImageView;
import com.mygdx.game.uix.TextView;

public class MenuScreen extends ScreenAdapter {
    private final MyGdxGame myGdxGame;
    private final ImageView backgroundView;
    private final TextView titleView;
    private final ButtonView startButtonView;
    private final ButtonView settingsButtonView;
    private final ButtonView exitButtonView;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new ImageView(0, 0, GameResources.BACKGROUND_IMG_PATH);
        backgroundView.setWidth(GameSettings.SCREEN_WIDTH);
        backgroundView.setHeight(GameSettings.SCREEN_HEIGHT);
        titleView = new TextView(myGdxGame.getLargeWhiteFont(), 184, 970, "SPACE CLEANER");
        startButtonView = new ButtonView(140, 655, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "ИГРАТЬ");
        settingsButtonView = new ButtonView(140, 555, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "НАСТРОЙКИ");
        exitButtonView = new ButtonView(140, 455, 440, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_LONG_BG_IMG_PATH, "ВЫХОД");
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.getCamera().update();
        myGdxGame.getBatch().setProjectionMatrix(myGdxGame.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.getBatch().begin();
        backgroundView.draw(myGdxGame.getBatch());
        titleView.draw(myGdxGame.getBatch());
        startButtonView.draw(myGdxGame.getBatch());
        settingsButtonView.draw(myGdxGame.getBatch());
        exitButtonView.draw(myGdxGame.getBatch());
        myGdxGame.getBatch().end();
    }

    private void handleInput() {
        if (!Gdx.input.justTouched()) {
            return;
        }
        myGdxGame.touch = myGdxGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.getGame().restartGame();
            myGdxGame.setScreen(myGdxGame.getGame());
        } else if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            myGdxGame.setScreen(myGdxGame.getSettings());
        } else if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        startButtonView.dispose();
        settingsButtonView.dispose();
        exitButtonView.dispose();
    }
}

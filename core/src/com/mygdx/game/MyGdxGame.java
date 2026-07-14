package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameStatus;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.uix.FontBuilder;

public class MyGdxGame extends Game {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameScreen game;
    public World world;
    public Vector3 touch;
    private float accumulator;
    private BitmapFont font;
    private BitmapFont largeWhiteFont;
    private BitmapFont commonBlackFont;
    private MenuScreen menu;
    private AudioManager audioManager;
    private SettingsScreen settings;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0f, 0f), true);
        world.setContactListener(new ContactManager(this));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        touch = new Vector3();

        font = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        largeWhiteFont = FontBuilder.generate(46, Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);

        audioManager = new AudioManager();
        menu = new MenuScreen(this);
        settings = new SettingsScreen(this);
        game = new GameScreen(this);
        setScreen(menu);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void stepWorld(float delta) {
        float safeDelta = Math.min(delta, 0.25f);
        accumulator += safeDelta;
        while (accumulator >= GameSettings.STEP_TIME) {
            world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
            accumulator -= GameSettings.STEP_TIME;
        }
    }

    @Override
    public void render() {
        Screen current = getScreen();
        if (current == game && game.getSession().getState() == GameStatus.PLAYING) {
            stepWorld(Gdx.graphics.getDeltaTime());
        }
        super.render();
    }

    public BitmapFont getFont() {
        return font;
    }

    public BitmapFont getLargeWhiteFont() {
        return largeWhiteFont;
    }

    public BitmapFont getCommonBlackFont() {
        return commonBlackFont;
    }

    public GameScreen getGame() {
        return game;
    }

    public MenuScreen getMenu() {
        return menu;
    }

    public World getWorld() {
        return world;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public SettingsScreen getSettings() {
        return settings;
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().hide();
        }
        game.dispose();
        menu.dispose();
        settings.dispose();
        audioManager.dispose();
        font.dispose();
        largeWhiteFont.dispose();
        commonBlackFont.dispose();
        batch.dispose();
        world.dispose();
    }
}

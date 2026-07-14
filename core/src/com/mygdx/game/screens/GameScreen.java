package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.BulletObject;
import com.mygdx.game.components.ShipObject;
import com.mygdx.game.components.TrashObject;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.uix.ButtonView;
import com.mygdx.game.uix.ImageView;
import com.mygdx.game.uix.LiveView;
import com.mygdx.game.uix.MovingBackgroundView;
import com.mygdx.game.uix.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {
    private static final Random RANDOM = new Random();

    MyGdxGame myGdxGame;
    ShipObject shipObject;
    GameSession session;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;
    ButtonView continueButton;
    ButtonView homeButton;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;
    private ImageView pauseOverlay;
    private TextView pauseTextView;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        session = new GameSession();
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, GameSettings.SCREEN_HEIGHT - 100, GameResources.TOP_IMAGE_PATH);
        scoreTextView = new TextView(myGdxGame.getFont(), 28, 1242, "ОЧКИ: 0");
        liveView = new LiveView(280, 1217, 3);
        pauseButton = new ButtonView(632, 1211, 54, 58, GameResources.PAUSE_IMG_PATH);
        continueButton = new ButtonView(282, 585, 70, 70, GameResources.RESUME_IMG_PATH);
        homeButton = new ButtonView(370, 585, 70, 70, GameResources.HOME_IMG_PATH);
        pauseOverlay = new ImageView(85, 405, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.getLargeWhiteFont(), 270, 760, "ПАУЗА");
        recordsTextView = new TextView(myGdxGame.getLargeWhiteFont(), 205, 890, "РЕКОРДЫ");
        recordsListView = new RecordsListView(myGdxGame.getLargeWhiteFont(), 770);
        homeButton2 = new ButtonView(280, 330, 160, 70, myGdxGame.getCommonBlackFont(),
                GameResources.BUTTON_SHORT_BG_IMG_PATH, "В МЕНЮ");
    }

    private void changeStatusGame(Vector3 touchPos) {
        if (session.getState() == GameStatus.PLAYING
                && pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            session.pauseGame(this);
            shipObject.getBody().setLinearVelocity(0f, 0f);
        } else if (session.getState() == GameStatus.PAUSED) {
            if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                session.resumeGame(this);
            } else if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                session.endGame();
                myGdxGame.setScreen(myGdxGame.getMenu());
            }
        }
    }

    private void handleInput() {
        if (!Gdx.input.isTouched()) {
            if (shipObject != null && session.getState() == GameStatus.PLAYING) {
                shipObject.getBody().setLinearVelocity(0f, 0f);
            }
            return;
        }

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        myGdxGame.touch = myGdxGame.getCamera().unproject(new Vector3(touchPos));

        if (Gdx.input.justTouched()) {
            GameStatus previousState = session.getState();
            changeStatusGame(touchPos);
            if (session.getState() == GameStatus.ENDED
                    && homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.getMenu());
                return;
            }
            if (previousState != session.getState()) {
                return;
            }
        }

        if (session.getState() == GameStatus.PLAYING
                && !pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
            shipObject.move(myGdxGame.touch);
        }
    }

    private void draw() {
        myGdxGame.getCamera().update();
        myGdxGame.getBatch().setProjectionMatrix(myGdxGame.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.getBatch().begin();
        backgroundView.draw(myGdxGame.getBatch());

        for (TrashObject trash : trashArray) {
            trash.draw(myGdxGame.getBatch());
        }
        for (BulletObject bullet : bulletArray) {
            bullet.draw(myGdxGame.getBatch());
        }
        if (shipObject != null) {
            shipObject.draw(myGdxGame.getBatch());
        }

        topBlackoutView.draw(myGdxGame.getBatch());
        liveView.draw(myGdxGame.getBatch());
        scoreTextView.draw(myGdxGame.getBatch());

        if (session.getState() == GameStatus.PLAYING) {
            pauseButton.draw(myGdxGame.getBatch());
        } else if (session.getState() == GameStatus.PAUSED) {
            pauseOverlay.draw(myGdxGame.getBatch());
            pauseTextView.draw(myGdxGame.getBatch());
            continueButton.draw(myGdxGame.getBatch());
            homeButton.draw(myGdxGame.getBatch());
        } else {
            pauseOverlay.draw(myGdxGame.getBatch());
            recordsTextView.draw(myGdxGame.getBatch());
            recordsListView.draw(myGdxGame.getBatch());
            homeButton2.draw(myGdxGame.getBatch());
        }

        myGdxGame.getBatch().end();
    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            TrashObject trash = trashArray.get(i);
            trash.update();

            if (!trash.isAlive()) {
                if (trash.wasDestroyedByBullet()) {
                    session.destructionRegistration();
                }
                myGdxGame.getAudioManager().playExplosion();
                removeTrash(i--);
                continue;
            }

            if (!trash.isInFrame()) {
                shipObject.hit();
                removeTrash(i--);
            }
        }
    }

    private void removeTrash(int index) {
        TrashObject trash = trashArray.remove(index);
        myGdxGame.world.destroyBody(trash.getBody());
        trash.dispose();
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            BulletObject bullet = bulletArray.get(i);
            bullet.update();
            if (bullet.hasToBeDestroyed()) {
                bulletArray.remove(i--);
                myGdxGame.world.destroyBody(bullet.getBody());
                bullet.dispose();
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (session.getState() == GameStatus.PLAYING) {
            shipObject.update();
            updateTrash();
            updateBullets();

            if (!shipObject.isAlive()) {
                finishGame();
            } else {
                if (session.shouldSpawnTrash()) {
                    trashArray.add(createTrash());
                }
                if (shipObject.needToShoot()) {
                    bulletArray.add(new BulletObject(
                            GameResources.BULLET_IMG_PATH,
                            Math.round(shipObject.getX()),
                            Math.round(shipObject.getY() + shipObject.getHeight() / 2f),
                            GameSettings.BULLET_WIDTH,
                            GameSettings.BULLET_HEIGHT,
                            myGdxGame.world
                    ));
                    myGdxGame.getAudioManager().playShoot();
                }
                backgroundView.move(delta);
                liveView.setLeftLives(shipObject.getLivesLeft());
                session.updateScore();
                scoreTextView.setText("ОЧКИ: " + session.getScore());
            }
        }

        draw();
    }

    private TrashObject createTrash() {
        String[] textures = {
                GameResources.TRASH_IMG_PATH,
                GameResources.TRASH_IMG1_PATH,
                GameResources.TRASH_IMG2_PATH,
                GameResources.TRASH_IMG3_PATH,
                GameResources.TRASH_IMG4_PATH
        };
        String path = textures[RANDOM.nextInt(textures.length)];
        int width = GameSettings.TRASH_WIDTH + RANDOM.nextInt(25);
        int height = GameSettings.TRASH_HEIGHT + RANDOM.nextInt(20);
        return new TrashObject(width, height, path, myGdxGame.world);
    }

    private void finishGame() {
        session.endGame();
        recordsListView.setRecords(MemoryManager.loadRecordsTable());
        shipObject.getBody().setLinearVelocity(0f, 0f);
    }

    protected void restartGame() {
        clearObjects();
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2,
                150,
                GameSettings.SHIP_WIDTH,
                GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
        liveView.setLeftLives(3);
        scoreTextView.setText("ОЧКИ: 0");
        session.startGame();
    }

    private void clearObjects() {
        for (TrashObject trash : trashArray) {
            myGdxGame.world.destroyBody(trash.getBody());
            trash.dispose();
        }
        trashArray.clear();

        for (BulletObject bullet : bulletArray) {
            myGdxGame.world.destroyBody(bullet.getBody());
            bullet.dispose();
        }
        bulletArray.clear();

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.getBody());
            shipObject.dispose();
            shipObject = null;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public GameSession getSession() {
        return session;
    }

    public MyGdxGame getMyGdxGame() {
        return myGdxGame;
    }

    @Override
    public void dispose() {
        clearObjects();
        backgroundView.dispose();
        topBlackoutView.dispose();
        liveView.dispose();
        pauseButton.dispose();
        continueButton.dispose();
        homeButton.dispose();
        homeButton2.dispose();
        pauseOverlay.dispose();
    }
}

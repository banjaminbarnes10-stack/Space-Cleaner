package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameStatus;

import java.util.ArrayList;

public class GameSession {
    private GameStatus state = GameStatus.ENDED;
    long nextTrashSpawnTime;
    long sessionStartTime;
    private long pauseStartTime;
    private int score;
    private int destructedTrashNumber;

    public void startGame() {
        score = 0;
        destructedTrashNumber = 0;
        sessionStartTime = TimeUtils.millis();
        pauseStartTime = 0L;
        state = GameStatus.PLAYING;
        scheduleNextTrash(sessionStartTime);
    }

    public boolean shouldSpawnTrash() {
        if (state != GameStatus.PLAYING) {
            return false;
        }
        long now = TimeUtils.millis();
        if (now < nextTrashSpawnTime) {
            return false;
        }
        scheduleNextTrash(now);
        return true;
    }

    private void scheduleNextTrash(long now) {
        double delay = GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown();
        delay = Math.max(GameSettings.MIN_TRASH_APPEARANCE_COOL_DOWN, delay);
        nextTrashSpawnTime = now + (long) delay;
    }

    private float getTrashPeriodCoolDown() {
        long elapsed = Math.max(0L, TimeUtils.millis() - sessionStartTime);
        return (float) Math.exp(-elapsed / 55000.0);
    }

    public void pauseGame(GameScreen game) {
        if (state == GameStatus.PLAYING) {
            state = GameStatus.PAUSED;
            pauseStartTime = TimeUtils.millis();
        }
    }

    public void resumeGame(GameScreen game) {
        if (state == GameStatus.PAUSED) {
            long pauseDuration = TimeUtils.millis() - pauseStartTime;
            sessionStartTime += pauseDuration;
            nextTrashSpawnTime += pauseDuration;
            state = GameStatus.PLAYING;
        }
    }

    public void updateScore() {
        if (state == GameStatus.PLAYING) {
            int survivalPoints = (int) ((TimeUtils.millis() - sessionStartTime) / 100L);
            score = survivalPoints + destructedTrashNumber * 100;
        }
    }

    public void destructionRegistration() {
        if (state == GameStatus.PLAYING) {
            destructedTrashNumber++;
        }
    }

    public void endGame() {
        if (state == GameStatus.ENDED) {
            return;
        }
        updateScore();
        state = GameStatus.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        int index = 0;
        while (index < recordsTable.size() && recordsTable.get(index) >= score) {
            index++;
        }
        recordsTable.add(index, score);
        while (recordsTable.size() > 5) {
            recordsTable.remove(recordsTable.size() - 1);
        }
        MemoryManager.saveTableOfRecords(recordsTable);
    }

    public GameStatus getState() {
        return state;
    }

    public void setState(GameStatus state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

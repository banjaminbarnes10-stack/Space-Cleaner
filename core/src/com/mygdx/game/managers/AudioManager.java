package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameResources;

public class AudioManager implements Disposable {
    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;
    private boolean soundEnabled;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));
        backgroundMusic.setVolume(0.12f);
        backgroundMusic.setLooping(true);
        updateMusicFlag();
        updateSoundFlag();
    }

    public void updateMusicFlag() {
        if (MemoryManager.loadIsMusicOn()) {
            if (!backgroundMusic.isPlaying()) {
                backgroundMusic.play();
            }
        } else {
            backgroundMusic.pause();
        }
    }

    public void updateSoundFlag() {
        soundEnabled = MemoryManager.loadIsSoundOn();
    }

    public void playShoot() {
        if (soundEnabled) {
            shootSound.play(0.18f);
        }
    }

    public void playExplosion() {
        if (soundEnabled) {
            explosionSound.play(0.28f);
        }
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        shootSound.dispose();
        explosionSound.dispose();
    }
}

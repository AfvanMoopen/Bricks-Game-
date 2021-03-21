package com.Hale.bricks.Objects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.Hale.bricks.Objects.ResourceManager.getMusic;
import static com.Hale.bricks.Objects.ResourceManager.getSound;



public class SoundManager {

    public static Music musicBackground;
    public static Sound brickSound;

    public static void loadSounds(){
        musicBackground = getMusic("music.mp3");
        brickSound = getSound("sound.ogg");
    }

    public static void playMusicBackground(){
        musicBackground.setLooping(true);
        musicBackground.play();
    }

    public static void stopMusicBackground(){
        musicBackground.dispose();
        musicBackground.stop();
    }

    public static void playBrickSound(){
        brickSound.play();
    }
}

package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class DataHandler {

    public static Preferences prefs;
    public static int health;
    public static int tier;
    public static int money;
    public static int highscore;

    public static boolean soundMuted, musicMuted;

    public static void load(){

        prefs = Gdx.app.getPreferences("SSTL_prefs");
        money = prefs.getInteger("money");

        highscore = prefs.getInteger("highscore");
        health = prefs.getInteger("health");
    }

    public static void save(){


    }

    public static void muteSound() {

        if (!soundMuted) {
            soundMuted = true;
        } else {
            soundMuted = false;
        }
    }

    public static void muteMusic() {

        if (!musicMuted) {
            musicMuted = true;
            //AssetLoader.theme.setVolume(0);

        } else {
            musicMuted = false;
            //AssetLoader.theme.setVolume(0.5f);
            //AssetLoader.theme.play();

        }
    }
}


package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class DataHandler {

    public static Preferences prefs;
    public static int healthLevel;
    public static int speedLevel;
    public static int money;
    public static int highscore;

    public static boolean soundMuted, musicMuted;

    public static void load(){

        prefs = Gdx.app.getPreferences("SSTL_prefs");
        money = prefs.getInteger("money");
        //highscore = prefs.getInteger("highscore");
        healthLevel = prefs.getInteger("healthlevel");
        if(healthLevel < 3){
            healthLevel = 3;
        }
/*        speedLevel = prefs.getInteger("speedlevel");
        if(speedLevel == 0){
            speedLevel = 1;
        }*/

        speedLevel = prefs.getInteger("speedlevel");
        if(speedLevel < 3){
            speedLevel = 3;
        }
    }

    public static void save(){

        //prefs.putInteger("highscore", highscore);
        prefs.putInteger("money", money);
        //prefs.putInteger("health", healthLevel);
        //prefs.putInteger("speedlevel", speedLevel);

        prefs.putBoolean("soundMuted", soundMuted);
        prefs.putBoolean("musicMuted", musicMuted);
        prefs.flush();


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
            AssetLoader.theme.setVolume(0);

        } else {
            musicMuted = false;
            AssetLoader.theme.setVolume(0.5f);
            AssetLoader.theme.play();

        }
    }
}


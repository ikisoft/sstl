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
    public static int kineticBarrierLevel;
    public static int shieldLevel;
    public static int money;
    public static int scrap;
    public static int highscore;
    public static int upgradeSelected;
    public static int shopHelper;

    public static boolean soundMuted, musicMuted;

    public static void load(){

        upgradeSelected = 0;
        prefs = Gdx.app.getPreferences("SSTL_prefs");
        money = prefs.getInteger("money");
        scrap = prefs.getInteger("scrap");
        //highscore = prefs.getInteger("highscore");

        healthLevel = prefs.getInteger("healthlevel");
        if(healthLevel < 3){
            healthLevel = 3;
        }
        speedLevel = prefs.getInteger("speedlevel");
        if(speedLevel < 1){
            speedLevel = 1;
        }
        kineticBarrierLevel = prefs.getInteger("barrierlevel");
        if(kineticBarrierLevel < 1){
            kineticBarrierLevel = 1;
        }
        shieldLevel = prefs.getInteger("shieldlevel");
        if(shieldLevel < 1){
            shieldLevel = 1;
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


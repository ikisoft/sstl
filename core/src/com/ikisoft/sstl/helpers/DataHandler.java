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
    public static int controlLevel;
    public static int kineticBarrierLevel;
    public static int shieldLevel;
    public static int vehicleCondition;
    public static int money;
    public static int scrap;
    public static int core;
    public static int rod;
    public static int upgradeSelected;
    public static int shopHelper;
    public static boolean crateCollected;

    public static boolean soundMuted, musicMuted;

    public static void load(){

        upgradeSelected = 0;
        prefs = Gdx.app.getPreferences("SSTL_prefs");
        money = prefs.getInteger("money");
        scrap = prefs.getInteger("scrap");
        rod = prefs.getInteger("rod");
        core = prefs.getInteger("core");
        //highscore = prefs.getInteger("highscore");

        healthLevel = prefs.getInteger("health_level");
        if(healthLevel < 3){
            healthLevel = 3;
        }

        speedLevel = prefs.getInteger("speed_level");
        if(speedLevel < 1){
            speedLevel = 1;
        }
        speedLevel = 1;
        controlLevel = prefs.getInteger("control_level");
        if(controlLevel < 1){
            controlLevel = 1;
        }
        controlLevel = 5;
        kineticBarrierLevel = prefs.getInteger("barrier_level");
        if(kineticBarrierLevel < 1){
            kineticBarrierLevel = 1;
        }
        shieldLevel = prefs.getInteger("shield_level");
        if(shieldLevel < 1){
            shieldLevel = 1;
        }

        vehicleCondition = prefs.getInteger("vehicle_condition");
        vehicleCondition = 100;

        crateCollected = false;
    }

    public static void save(){

        prefs.putInteger("money", money);
        prefs.putInteger("scrap", scrap);
        prefs.putInteger("rod", rod);
        prefs.putInteger("core", core);
        prefs.putInteger("health", healthLevel);
        prefs.putInteger("speedlevel", speedLevel);

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


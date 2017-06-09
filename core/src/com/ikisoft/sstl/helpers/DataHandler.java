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
    public static int epicArmor;
    public static int epicThruster;
    public static int epicKinetic;
    public static int epicEnergy;
    public static int woodenCrate;
    public static int steelCrate;
    public static int upgradeSelected;
    public static int itemSelected;
    public static boolean crateCollected;
    public static boolean soundMuted, musicMuted;



    public static void load() {

        upgradeSelected = 0;
        itemSelected = 0;
        prefs = Gdx.app.getPreferences("SSTL_prefs");
        money = prefs.getInteger("money");
        money = 10000000;
        scrap = prefs.getInteger("scrap");
        rod = prefs.getInteger("rod");
        core = prefs.getInteger("core");
        epicArmor = prefs.getInteger("epic_armor");
        epicThruster = prefs.getInteger("epic_thruster");
        epicKinetic = prefs.getInteger("epic_kinetic");
        epicEnergy = prefs.getInteger("epic_energy");
        woodenCrate = prefs.getInteger("wooden_crate");
        steelCrate = prefs.getInteger("steel_crate");

        epicArmor = 0;
        epicThruster = 0;
        epicKinetic = 0;
        epicEnergy = 0;
        //highscore = prefs.getInteger("highscore");

        healthLevel = prefs.getInteger("health_level");
        if(healthLevel < 3){
            healthLevel = 3;
        }

        speedLevel = prefs.getInteger("speed_level");
        if(speedLevel < 1){
            speedLevel = 1;
        }
        speedLevel = 5;
        controlLevel = prefs.getInteger("control_level");
        if(controlLevel < 1){
            controlLevel = 1;
        }
        //controlLevel = 5;
        kineticBarrierLevel = prefs.getInteger("barrier_level");
        if(kineticBarrierLevel < 1){
            kineticBarrierLevel = 1;
        }
        //kineticBarrierLevel = 10;
        shieldLevel = prefs.getInteger("shield_level");
        if(shieldLevel < 1){
            shieldLevel = 1;
        }
        //shieldLevel = 1;

        vehicleCondition = prefs.getInteger("vehicle_condition");
        vehicleCondition = 100;

        crateCollected = false;
    }

    public static void save() {

        prefs.putInteger("money", money);
        prefs.putInteger("scrap", scrap);
        prefs.putInteger("rod", rod);
        prefs.putInteger("core", core);
        prefs.putInteger("health_level", healthLevel);
        prefs.putInteger("speed_level", speedLevel);
        prefs.putInteger("barrier_level", kineticBarrierLevel);
        prefs.putInteger("shield_level", shieldLevel);
        prefs.putInteger("vehicle_condition", vehicleCondition);
        prefs.putInteger("wooden_crate", woodenCrate);
        prefs.putInteger("steel_crate", steelCrate);
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


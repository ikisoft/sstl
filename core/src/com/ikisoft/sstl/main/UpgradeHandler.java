package com.ikisoft.sstl.main;

import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;

/**
 * Created by Max on 31.5.2017.
 */

public class UpgradeHandler {
    private static boolean armorReq;
    private static boolean thrusterReq;
    private static boolean kineticReq;
    private static boolean energyReq;
    private static int kineticLevel;
    private static int shieldLevel;

    public static void upgradeArmor() {

        if (DataHandler.money >= (int) (Math.sqrt(DataHandler.healthLevel * 1000))
                && DataHandler.scrap >= (DataHandler.healthLevel * 10)) {

            DataHandler.money -= (int) (Math.sqrt(DataHandler.healthLevel * 1000));
            DataHandler.scrap -= DataHandler.healthLevel * 10;
            DataHandler.healthLevel++;
            AssetLoader.upgrade.play();
        }
    }

    public static void upgradeThruster() {

        if (DataHandler.money >= (int) (Math.sqrt(DataHandler.speedLevel) * 1000)
                && DataHandler.scrap >= DataHandler.speedLevel * 5
                && DataHandler.rod >= DataHandler.speedLevel) {

            DataHandler.money -= (int) (Math.sqrt(DataHandler.speedLevel) * 1000);
            DataHandler.scrap -= DataHandler.speedLevel * 5;
            DataHandler.rod -= DataHandler.speedLevel;
            DataHandler.speedLevel++;
            AssetLoader.upgrade.play();
        }
    }

    public static void upgradeKinetic() {

        kineticLevel = DataHandler.kineticBarrierLevel;

        if (DataHandler.money >= (int) (Math.sqrt(kineticLevel) * 1000)
                && DataHandler.scrap >= kineticLevel * 2
                && DataHandler.rod >= kineticLevel * 2) {

            DataHandler.money -= (int) (Math.sqrt(kineticLevel) * 1000);
            DataHandler.scrap -= kineticLevel * 2;
            DataHandler.rod -= kineticLevel * 2;
            DataHandler.kineticBarrierLevel++;
            AssetLoader.upgrade.play();
        }

    }

    public static void upgradeEnergyShield() {

        shieldLevel = DataHandler.shieldLevel;

        if (DataHandler.money >= (int) (Math.sqrt(shieldLevel) * 1200)
                && DataHandler.rod >= shieldLevel * 3
                && DataHandler.core >= shieldLevel) {

            DataHandler.money -= (int) (Math.sqrt(shieldLevel) * 1200);
            DataHandler.rod -= shieldLevel * 3;
            DataHandler.core -= shieldLevel;
            DataHandler.shieldLevel++;
            AssetLoader.upgrade.play();

        }


    }

    public static boolean canUpgradeArmor() {

        if (DataHandler.money >= (int) (Math.sqrt(DataHandler.speedLevel) * 1000)
                && DataHandler.scrap >= DataHandler.speedLevel * 5
                && DataHandler.rod >= DataHandler.speedLevel) {
            return true;
        }
        return false;
    }


    public static boolean canUpgradeThruster() {

        if (DataHandler.money >= (int) (Math.sqrt(DataHandler.speedLevel) * 1000)
                && DataHandler.scrap >= DataHandler.speedLevel * 5
                && DataHandler.rod >= DataHandler.speedLevel) {
            return true;
        }
        return false;
    }


    public static boolean canUpgradeKinetic() {

        kineticLevel = DataHandler.kineticBarrierLevel;

        if (DataHandler.money >= (int) (Math.sqrt(kineticLevel) * 1000)
                && DataHandler.scrap >= kineticLevel * 2
                && DataHandler.rod >= kineticLevel * 2) {
            return true;
        }
        return false;
    }

    public static boolean canUpgradeEnergyShield() {

        shieldLevel = DataHandler.shieldLevel;

        if (DataHandler.money >= (int) (Math.sqrt(shieldLevel) * 1200)
                && DataHandler.rod >= shieldLevel * 3
                && DataHandler.core >= shieldLevel) {
            return true;
        }
        return false;
    }


    public static void repair() {

    }
}

package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ikisoft.sstl.main.Updater;

/**
 * Created by Max on 27.5.2017.
 */

public class StringConstants {



    public static String logoText, infoText, shopText, optionsText, gameoverText, armorText,
            crateSplashContinue,
            thrusterText, kineticText, energyShieldText, gameoverText2;

    public static void loadStrings(Updater updater){
        logoText = "   (Slightly)\nSlower Than Light";
        infoText = "GFH JAM 2017\n" +
                "  ikisoft";
        shopText = "ARMOR: " + DataHandler.healthLevel + "\n" +
                "THRUSTERS: " + DataHandler.speedLevel + "\n" +
                "KINETIC BARRIER: " + DataHandler.kineticBarrierLevel + "\n" +
                "ENERGY SHIELD: " + DataHandler.shieldLevel;

        optionsText = "here are some\noptionsText!";
        gameoverText = "oh dear, you ran into\nsome junk... ;(";
        gameoverText2 = "Max speed: " + "" + "\n" +
                "Money collected: " + updater.getMoneyThisRun() + "\n" +
                "Ship durability -" + "" + "\n" +
                "Distance: " + updater.getDistance();
        armorText = "The armor plating of your spaceship\n" +
                "determines how many hits you can\n" +
                "take before your ship breaks.";
        thrusterText = "Thrusters increase your horizontal\nspeed" +
                " but they also increase\ndrifting.";
        kineticText = "Kinetic barrier reduces the speed\n" +
                "penalty when hitting something.";
        energyShieldText = "Energy shield will protect your\nships" +
                " hull from damaging impacts.\n" +
                "Needs time to recharge.";
        crateSplashContinue = "Open later";
    }
}

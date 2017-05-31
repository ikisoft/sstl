package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ikisoft.sstl.main.Updater;

import java.util.ArrayList;

/**
 * Created by Max on 27.5.2017.
 */

public class StringConstants {



    public static String logoText, infoText, shopText, optionsText, gameoverText, armorText,
            crateSplashContinue,
            thrusterText, kineticText, energyShieldText, gameoverText2, repairText,
    random1, random2, random3, random4, random5, random6, random7, random8, random9,
    random10, random11;

    public static ArrayList<String> randomMemes = new ArrayList<String>();


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
        repairText = "Keep your vehicle condition good\n" +
                "or your vehicle's performance\n" +
                "will suffer. If vehicle\ncondition goes " +
                "to zero, game over.";

        random1 ="in space, nobody can hear\nyou scream. ;)";
        random2 = "did you know that the\nmoon landing was 120 years ago?";
        random3 = "bloody space pirates.";
        random4 = "the moon is actually cheese.";
        random5 = "the earth was originally round,\nbut it became flat because\nof all the weight.";
        random6 = "did you know that if a tree\nfalls in space, no one hears it?";
        random7 = "the moonlandings were faked\n" +
                "by the US government to make\n" +
                "the soviet union go bankrupt.";
        random8 = "if the majority of earth is water,\n" +
                "would a planet that is mostly earth\n" +
                "be called water?";

        random9 = "Spaceship fuel can\nsmelt steel beams";

        randomMemes.add(0, random1);
        randomMemes.add(1, random2);
        randomMemes.add(2, random3);
        randomMemes.add(3, random4);
        randomMemes.add(4, random5);
        randomMemes.add(5, random6);
        randomMemes.add(6, random7);
        randomMemes.add(7, random8);
        randomMemes.add(8, random9);

    }
}

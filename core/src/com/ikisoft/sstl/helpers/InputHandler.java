package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ikisoft.sstl.main.Updater;
import com.ikisoft.sstl.main.UpgradeHandler;
import com.sun.org.apache.bcel.internal.generic.DADD;

import javax.xml.crypto.Data;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class InputHandler implements InputProcessor {

    private Updater updater;
    private float screenWidth;
    private float screenHeight;
    private Button menuButton, shopButton;
    float delta;
    private boolean fuckThis;

    public InputHandler(Updater updater, float screenWidth, float screenHeight) {

        this.updater = updater;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        menuButton = new Button(screenWidth / 3, 100);
        shopButton = new Button(256, 256);
        fuckThis = false;


    }


    @Override
    public boolean keyDown(int keycode) {

        System.err.println("keydown");

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updater.getSpacecraft().setMovingLeftTrue();
            return true;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updater.getSpacecraft().setMovingRightTrue();
            return true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)
                && Gdx.input.isKeyPressed(Input.Keys.E)
                && Gdx.input.isKeyPressed(Input.Keys.V)) {
            updater.setDevEnabled();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        updater.getSpacecraft().setMovingLeftFalse();
        updater.getSpacecraft().setMovingRightFalse();

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        x = (int) scaleX(x);
        y = (int) scaleY(y);
        System.out.println("x: " + x);
        System.out.println("y: " + y);


        switch (updater.getGameState()) {

            case START:

                if (menuButton.isDown(x, y, 540, 1000)) {
                    updater.setGameState(Updater.GameState.RUNNING);
                } else if (menuButton.isDown(x, y, 540, 1130)) {
                    updater.setGameState(Updater.GameState.SHIP);
                } else if (menuButton.isDown(x, y, 540, 1270)) {
                    updater.setGameState(Updater.GameState.INFO);
                } else if (menuButton.isDown(x, y, 540, 1430)) {
                    updater.setGameState(Updater.GameState.OPTIONS);
                }

            case RUNNING:


                if (x < 540) {
                    updater.getSpacecraft().setMovingLeftTrue();
                } else if (x > 540) {
                    updater.getSpacecraft().setMovingRightTrue();
                }

                //fuck touch input
                //bubble gum fix for start game move bug...
                if (!fuckThis) {
                    updater.getSpacecraft().setMovingLeftFalse();
                    updater.getSpacecraft().setMovingRightFalse();
                }

                fuckThis = true;

                break;

            case GAMEOVER:
                break;

            case GAMEOVERSCREEN:

                if (menuButton.isDown(x, y, 540, 1130)) {
                    updater.setGameState(Updater.GameState.SHIP);
                } else if (menuButton.isDown(x, y, 540, 1430)) {
                    updater.resetGame();
                }

                break;

            case INFO:

                if (menuButton.isDown(x, y, 540, 1430)) {
                    updater.setGameState(Updater.GameState.START);
                }

                break;

            case SHIP:

                //Defensive Systems
                if (menuButton.isDown(x, y, 540, 1007)) {
                    updater.setGameState(Updater.GameState.SYSTEMS);
                } else if (menuButton.isDown(x, y, 540, 1156)) {
                    updater.setGameState(Updater.GameState.ITEMS);
                } else if (menuButton.isDown(x, y, 540, 1310)) {
                    updater.setGameState(Updater.GameState.REPAIR);
                } else if (menuButton.isDown(x, y, 540, 1458)) {
                    updater.setGameState(Updater.GameState.STATS);
                } else if (menuButton.isDown(x, y, 538, 1650)) {
                    updater.resetGame();
                    updater.setGameState(Updater.GameState.RUNNING);
                }

                break;
            case SYSTEMS:

                //HEALTH
                if (shopButton.isDown(x, y, 162, 652)) {
                    DataHandler.upgradeSelected = 1;

                } else if (shopButton.isDown(x, y, 408, 652)) {
                    DataHandler.upgradeSelected = 2;

                } else if (shopButton.isDown(x, y, 670, 652)) {
                    DataHandler.upgradeSelected = 3;

                } else if (shopButton.isDown(x, y, 940, 652)) {
                    DataHandler.upgradeSelected = 4;

                } else if (menuButton.isDown(x, y, 538, 1600)) {
                    updater.setGameState(Updater.GameState.SHIP);

                } else if (menuButton.isDown(x, y, 540, 1206)) {
                    if (DataHandler.upgradeSelected == 1) {
                        UpgradeHandler.upgradeArmor();
                    } else if (DataHandler.upgradeSelected == 2) {
                        UpgradeHandler.upgradeThruster();
                    } else if (DataHandler.upgradeSelected == 3) {
                        UpgradeHandler.upgradeKinetic();
                    } else if (DataHandler.upgradeSelected == 4) {
                        UpgradeHandler.upgradeEnergyShield();
                    }
                }

                break;

            case ITEMS:

                if (menuButton.isDown(x, y, 538, 1600)) {

                    updater.setGameState(Updater.GameState.SHIP);
                } else if (shopButton.isDown(x, y, 162, 652)) {
                    DataHandler.itemSelected = 1;

                } else if (shopButton.isDown(x, y, 408, 652)) {
                    DataHandler.itemSelected = 2;

                } else if (shopButton.isDown(x, y, 670, 652)) {
                    DataHandler.itemSelected = 3;

                } else if (shopButton.isDown(x, y, 940, 652)) {
                    DataHandler.itemSelected = 4;

                } else if (menuButton.isDown(x, y, 540, 1206)) {
                    if (DataHandler.itemSelected == 1) {
                        System.out.println("ARMOR SOLD");
                    } else if (DataHandler.itemSelected == 2) {
                        System.out.println("THRUSTER SOLD");
                    } else if (DataHandler.itemSelected == 3) {
                        System.out.println("KINETIC SOLD");
                    } else if (DataHandler.itemSelected == 4) {
                        System.out.println("ENERGY SOLD");
                    }
                }

                break;

            case REPAIR:

                if (menuButton.isDown(x, y, 538, 1600)) {

                    updater.setGameState(Updater.GameState.SHIP);
                }

                break;

            case STATS:

                if (menuButton.isDown(x, y, 540, 1600)) {

                    updater.setGameState(Updater.GameState.SHIP);
                }

            case OPTIONS:

                if (menuButton.isDown(x, y, 540, 1430)) {

                    updater.setGameState(Updater.GameState.RUNNING);
                }

                break;

            case CRATESPLASH:

                if (menuButton.isDown(x, y, 540, 1706)) {
                    updater.setGameState(Updater.GameState.GAMEOVERSCREEN);
                } else if (menuButton.isDown(x, y, 540, 1256)) {
                    updater.setGameState(Updater.GameState.CRATECHOOSE);
                    //updater.openCrate();
                } else if(menuButton.isDown(x, y, 540, 1400)){
                    updater.nextCrate();
                }
                break;

            case CRATECHOOSE:

                if (menuButton.isDown(x, y, 540, 700)) {

                    updater.openCrate();
                } else if (menuButton.isDown(x, y, 540, 800)) {

                    updater.openCrate();
                } else if (menuButton.isDown(x, y, 540, 950)){

                    updater.setGameState(Updater.GameState.CRATESPLASH);
                }


                break;

            case ITEMSPLASH:

                if (menuButton.isDown(x, y, 540, 1706)) {

                    if (updater.getCrateQueue().isEmpty()) {
                        updater.setGameState(Updater.GameState.GAMEOVERSCREEN);
                    } else {
                        updater.setGameState(Updater.GameState.CRATESPLASH);
                    }

                }

        }

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        x = (int) scaleX(x);
        y = (int) scaleY(y);

        switch (updater.getGameState()) {

            case RUNNING:

                if (x < 540) {
                    updater.getSpacecraft().setMovingLeftFalse();
                } else if (x > 540) {
                    updater.getSpacecraft().setMovingRightFalse();
                }

                break;

            case GAMEOVER:
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private float scaleX(int screenX) {
        return (screenX / screenWidth) * 1080;
    }

    private float scaleY(int screenY) {
        return (screenY / screenHeight) * 1920;
    }
}


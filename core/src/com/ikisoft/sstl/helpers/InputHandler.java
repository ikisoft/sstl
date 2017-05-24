package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ikisoft.sstl.main.Updater;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class InputHandler implements InputProcessor {

    private Updater updater;
    private float screenWidth;
    private float screenHeight;
    private Button menuButton, shopButton;
    float delta;

    public InputHandler(Updater updater, float screenWidth, float screenHeight){

        this.updater = updater;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        menuButton = new Button(screenWidth/3, 100);
        shopButton = new Button(256, 256);

    }


    @Override
    public boolean keyDown(int keycode) {

        /*if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            updater.resetGame();
            return true;
         }*/
        System.out.println("keydown");

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            updater.getSpacecraft().setMovingLeftTrue();

            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updater.getSpacecraft().setMovingRightTrue();


           return true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)
                && Gdx.input.isKeyPressed(Input.Keys.E)
                && Gdx.input.isKeyPressed(Input.Keys.V)){
            updater.setDevEnabled();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyup");

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updater.getSpacecraft().setMovingLeftFalse();
            return true;
        }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updater.getSpacecraft().setMovingRightFalse();
            return true;
        }

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
        System.out.println("x: "+ x);
        System.out.println("y: " + y);



        switch (updater.getGameState()){

            case START:

               if(menuButton.isDown(x, y, 540, 1000)){
                   updater.setGameState(Updater.GameState.RUNNING);
               }
               if(menuButton.isDown(x, y, 540, 1130)){
                   updater.setGameState(Updater.GameState.SHOP);
               }
               if(menuButton.isDown(x, y, 540, 1270)){
                   updater.setGameState(Updater.GameState.INFO);
               }
               if(menuButton.isDown(x, y, 540, 1430)){
                   updater.setGameState(Updater.GameState.OPTIONS);
               }

            case RUNNING:

                if(x < 540){
                    updater.getSpacecraft().setMovingLeftTrue();
                }
                if(x > 540){
                    updater.getSpacecraft().setMovingRightTrue();
                }

                break;

            case GAMEOVER:

                if(menuButton.isDown(x, y, 540, 1130)){
                    updater.setGameState(Updater.GameState.SHOP);
                }

                if(menuButton.isDown(x, y, 540, 1430)){
                    updater.resetGame();
                }

                break;

            case INFO:

                if(menuButton.isDown(x, y, 540, 1430)){
                    updater.setGameState(Updater.GameState.START);
            }


                break;

            case SHOP:

                //HEALTH
                if(shopButton.isDown(x, y, 162, 1048)){

                    DataHandler.upgradeSelected = 1;

                    /*if(DataHandler.healthLevel < 10){

                        if(DataHandler.money >= 100 * DataHandler.healthLevel){
                            if(DataHandler.money - DataHandler.healthLevel >= 0){
                                DataHandler.healthLevel++;
                                DataHandler.money -= 100 * DataHandler.healthLevel;

                            }
                        }
                    }*/
                }

                //SPEED
                if(shopButton.isDown(x, y, 408, 1048)){

                    DataHandler.upgradeSelected = 2;

                    /*if(DataHandler.speedLevel < 10) {

                        if (DataHandler.money >= 75 * DataHandler.speedLevel) {
                            if (DataHandler.money - DataHandler.speedLevel >= 0) {
                                DataHandler.speedLevel++;
                                DataHandler.money -= 75 * DataHandler.speedLevel;

                            }
                        }
                    }*/
                }

                //Kinetic Barrier
                if(shopButton.isDown(x, y, 670, 1048)){

                    DataHandler.upgradeSelected = 3;

                }

                if(shopButton.isDown(x, y, 940, 1048)){

                    DataHandler.upgradeSelected = 4;

                }

                if(menuButton.isDown(x, y, 538, 1802)){

                    updater.resetGame();
                    updater.setGameState(Updater.GameState.RUNNING);
                }


                break;
            case OPTIONS:

                if(menuButton.isDown(x, y, 540, 1430)){

                    updater.setGameState(Updater.GameState.RUNNING);
                }

                break;
        }

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        switch (updater.getGameState()){

            case RUNNING:

                if(x < 270 && y > 960){
                    updater.getSpacecraft().setMovingLeftFalse();
                }
                if(x > 810 && y > 960){
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


package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.InputProcessor;
import com.ikisoft.sstl.main.Updater;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class InputHandler implements InputProcessor {

    private Updater updater;
    private int screenWidth;
    private int screenHeight;


    public InputHandler(Updater updater, int screenWidth, int screenHeight){

        this.updater = updater;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        switch (updater.getGameState()){

            case RUNNING:

                if(x < screenWidth / 2) updater.getSpacecraft().setMovingLeftTrue();
                if(x > screenWidth / 2) updater.getSpacecraft().setMovingRightTrue();

                break;
        }


        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        switch (updater.getGameState()){
            case RUNNING:

                if(x < screenWidth / 2) updater.getSpacecraft().setMovingLeftFalse();
                if(x > screenWidth / 2) updater.getSpacecraft().setMovingRightFalse();

                break;
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
}

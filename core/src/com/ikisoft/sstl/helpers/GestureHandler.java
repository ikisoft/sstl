package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.main.Updater;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class GestureHandler implements GestureDetector.GestureListener {

    private Updater updater;
    private Spacecraft spacecraft;
    int screenWidth;
    int screenHeight;

    public GestureHandler(Updater updater, Spacecraft spacecraft, int screenWidth, int screenHeight){

        this.updater = updater;
        this.spacecraft = spacecraft;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {



        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}

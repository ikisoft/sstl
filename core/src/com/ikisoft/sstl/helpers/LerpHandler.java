package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by maxikahe on 20.5.2017.
 */

public class LerpHandler {

    private Vector2 position;
    private Vector2 target;

    public LerpHandler(float initX, float initY, float targetX, float targetY){

        position = new Vector2(initX, initY);
        target = new Vector2(targetX, targetY);

    }

    public void reset(float posX, float posY){
        position.set(posX, posY);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setPosition(){

    }

    public Vector2 getTarget(){
        return target;
    }

    public void setTarget(){

    }

}

package com.ikisoft.sstl.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;

import java.util.Vector;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Spacecraft {

    private Vector2 position;
    private Rectangle hitbox;
    private boolean movingLeft, movingRight;
    private float thrustLeft, thrustRight, maxThrust, thrustPower, thrustDece;


    public Spacecraft(float x, float y, float width, float height){

        position = new Vector2(x, y);
        hitbox = new Rectangle();
        hitbox.setSize(width, height);
        movingLeft = false;
        movingRight = false;
        thrustLeft = 0;
        thrustRight = 0;
        maxThrust = 3;
        thrustPower = 0.1f;
        thrustDece = 0.03f;

    }

    public void update(float delta){



        if(movingLeft){
            thrustLeft += thrustPower;
            moveLeft(delta);
        } else if(movingRight){
            thrustRight += thrustPower;
            moveRight(delta);
        }

        position.x -= (10 * thrustLeft) * delta;
        position.x += (10 * thrustRight) * delta;

        thrustLeft -= thrustDece * delta;
        if(thrustLeft < 0)thrustLeft = 0;
        thrustRight -= thrustDece * delta;
        if(thrustRight < 0)thrustRight = 0;

        if(thrustLeft > maxThrust)thrustLeft = maxThrust;
        if(thrustRight > maxThrust)thrustRight = maxThrust;

        if(position.x < 1)position.x = 1;
        if(position.x > 1080 - hitbox.width)position.x = 1080 - hitbox.width;

        hitbox.x = position.x;
        hitbox.y = position.y;

    }

    public void moveLeft(float delta){

        position.x -= (10 * thrustLeft) * delta;
        thrustRight = 0;

    }

    public void moveRight(float delta){

        position.x += (10 * thrustRight) * delta;
        thrustLeft = 0;

    }

    public void reset(){
        setPosition(284, 256);
        hitbox.x = position.x;
        hitbox.y = position.y;
        thrustLeft = 0;
        thrustRight = 0;


    }

    public Vector2 getPosition(){
        return position;
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setMovingLeftTrue(){

        movingLeft = true;
        movingRight = false;

    }

    public void setMovingLeftFalse(){

        movingLeft = false;
    }

    public void setMovingRightTrue(){

        movingRight = true;
        movingLeft = false;

    }

    public void setMovingRightFalse(){

        movingRight = false;

    }


}

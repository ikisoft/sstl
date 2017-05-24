package com.ikisoft.sstl.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.sstl.helpers.DataHandler;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Spacecraft {

    private Vector2 position;
    private Rectangle hitbox;
    private boolean movingLeft, movingRight;
    private float velocityLeft, velocityRight, thrustPower;
    private int health;


    public Spacecraft(float x, float y, float width, float height) {

        System.out.println("creating spacecraft, health: " + health
                + " speed: " + thrustPower);

        position = new Vector2(x, y);
        hitbox = new Rectangle();
        hitbox.setSize(width, height);
        movingLeft = false;
        movingRight = false;
        velocityRight = 0;
        velocityLeft = 0;
        thrustPower = (float) DataHandler.speedLevel / 10; //0.3
        health = DataHandler.healthLevel;



    }

    public void update(float delta) {

        System.out.println("Thrustpower: " + thrustPower);

        velocityLeft -= 0.01;
        velocityRight -= 0.01;

        if(velocityLeft <= 0)velocityLeft = 0;
        if(velocityRight <= 0)velocityRight = 0;

        position.x -= velocityLeft * 5;
        position.x += velocityRight * 5;

        if(movingLeft){
            velocityLeft += thrustPower;
            if(velocityLeft >= 3)velocityLeft = 3;
            position.x -= velocityLeft;
            velocityRight -= 0.05;

        }

        if(movingRight){
            velocityRight += thrustPower;
            if(velocityRight >= 3)velocityRight = 3;
            position.x += velocityRight;
            velocityLeft -= 0.05;


        }

        System.out.println("Velocity Left: " + velocityLeft);
        System.out.println("Velocity Right: " + velocityRight);

        //position
        if (position.x <= 0){
            position.x = 0;
            velocityLeft = 0;
            velocityRight = 1;
        }
        if (position.x >= 1080 - hitbox.width){
            position.x = 1080 - hitbox.width;
            velocityRight = 0;
            velocityLeft = 1;
        }

        hitbox.x = position.x;
        hitbox.y = position.y;

    }

    public void reset() {
        setPosition(284, 384);
        hitbox.x = position.x;
        hitbox.y = position.y;
        movingLeft = false;
        movingRight = false;
        thrustPower = (float) DataHandler.speedLevel / 10;
        health = DataHandler.healthLevel;
        velocityLeft = 0;
        velocityRight = 0;


    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setMovingLeftTrue() {

        movingLeft = true;
        movingRight = false;

    }

    public void setMovingLeftFalse() {

        movingLeft = false;
    }

    public void setMovingRightTrue() {

        movingRight = true;
        movingLeft = false;

    }

    public void setMovingRightFalse() {

        movingRight = false;

    }

    public boolean getMovingLeft() {
        return movingLeft;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth() {
        health -= 1;
        System.out.println(health);
    }


}

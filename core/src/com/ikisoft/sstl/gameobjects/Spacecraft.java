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
    private int speedLeft, speedRight, maxThrust, thrustPower, speedDece;
    private int health, maxSpeed;


    public Spacecraft(float x, float y, float width, float height) {

        position = new Vector2(x, y);
        hitbox = new Rectangle();
        hitbox.setSize(width, height);
        movingLeft = false;
        movingRight = false;
        speedLeft = 0;
        speedRight = 0;

        maxSpeed = 3;
        speedDece = 1;
        maxThrust = 3;
        //3
        thrustPower = DataHandler.speedLevel;

        health = DataHandler.healthLevel;
        System.out.println("creating spacecraft, health: " + health
                + "speed: " + thrustPower);
        movingLeft = false;
        movingRight = false;


    }

    public void update(float delta) {

        if (movingLeft) {
            speedLeft += thrustPower;
            moveLeft(delta);
            //speedRight -= thrustPower * delta;
        }

        if (movingRight) {

            speedRight += thrustPower;
            moveRight(delta);
        }

        position.x -= speedLeft * 2 * delta;
        position.x += speedRight * 2 * delta;
        speedLeft--;
        speedRight--;

        if (speedLeft < 0) speedLeft = 0;
        if (speedRight < 0) speedRight = 0;

        if (speedLeft > maxThrust) speedLeft = maxThrust;
        if (speedRight > maxThrust) speedRight = maxThrust;

        if (position.x < 1) position.x = 1;
        if (position.x > 1080 - hitbox.width) position.x = 1080 - hitbox.width;

        hitbox.x = position.x;
        hitbox.y = position.y;

    }

    public void moveLeft(float delta) {

        position.x -= speedLeft * delta;
        speedRight = 0;

    }

    public void moveRight(float delta) {

        position.x += speedRight * delta;
        speedLeft = 0;

    }

    public void reset() {
        setPosition(284, 384);
        hitbox.x = position.x;
        hitbox.y = position.y;
        speedLeft = 0;
        speedRight = 0;
        movingLeft = false;
        movingRight = false;
        thrustPower = DataHandler.speedLevel;
        health = DataHandler.healthLevel;


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

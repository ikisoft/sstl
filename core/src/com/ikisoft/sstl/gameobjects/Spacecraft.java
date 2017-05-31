package com.ikisoft.sstl.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Spacecraft {

    private Vector2 position;
    private Rectangle hitbox;
    private boolean movingLeft, movingRight, soundPlayed, energyDestroyed, hit;
    private float velocityLeft, velocityRight, thrustPower;
    private int health;
    private float energy;


    public Spacecraft(float x, float y, float width, float height) {

        System.out.println("creating spacecraft, health: " + health
                + " speed: " + thrustPower);

        position = new Vector2(x, y);
        hitbox = new Rectangle();
        hitbox.setSize(width, height);
        hitbox.x = position.x;
        hitbox.y = position.y;

        movingLeft = false;
        movingRight = false;
        energyDestroyed = false;
        thrustPower = (float) DataHandler.speedLevel / 10;
        health = DataHandler.healthLevel;
        velocityLeft = 0;
        velocityRight = 0;
        energy = 33;
        soundPlayed = false;
        hit = false;

    }

    public void update(float delta) {


        if(!energyDestroyed)energy += 0.05;
        if(energy >= 100)energy = 100;

        velocityLeft -= 0.01;
        velocityRight -= 0.01;
        if(velocityLeft < 0)velocityLeft = 0;
        if(velocityRight < 0)velocityRight = 0;
        position.x -= velocityLeft * 5;
        position.x += velocityRight * 5;

        if(movingLeft && health != 0){

            //No way around this because of android audio playback and libgdx shittiness
            //must use gdx.sound instead of music...
            if(!soundPlayed){
                long id = AssetLoader.thruster1.play();
                AssetLoader.thruster1.setLooping(id, true);
                soundPlayed = true;
            }

            velocityLeft += thrustPower;
            position.x -= velocityLeft;
            //velocityRight -= 0.05;
            velocityRight -= thrustPower;
        }

        if(movingRight && health != 0){
            //No way around this because of android audio playback and libgdx shittiness
            //must use gdx.sound instead of music...
            if(!soundPlayed){
                long id = AssetLoader.thruster1.play();
                AssetLoader.thruster1.setLooping(id, true);
                soundPlayed = true;
            }

            velocityRight += thrustPower;
            position.x += velocityRight;
            //velocityLeft -= 0.05;
            velocityLeft -= thrustPower;
        }

        //position
        if (position.x <= 0){
            position.x = 0;
            velocityLeft = 0;
            velocityRight = 1.5f;
        }
        if (position.x >= 1080 - hitbox.width){
            position.x = 1080 - hitbox.width;
            velocityRight = 0;
            velocityLeft = 1.5f;
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
        energy = 33;
        energyDestroyed = false;

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
        AssetLoader.thruster1.stop();
        soundPlayed = false;

        movingLeft = false;
    }

    public void setMovingRightTrue() {

        movingRight = true;
        movingLeft = false;

    }

    public void setMovingRightFalse() {
        AssetLoader.thruster1.stop();
        soundPlayed = false;
        movingRight = false;

    }

    public boolean getMovingLeft() {
        return movingLeft;
    }

    public boolean getMovingRight() {
        return movingRight;
    }

    public void setVelocityLeft(float velocityLeft){
        this.velocityLeft = velocityLeft;
    }

    public void setVelocityRight(float velocityRight){
        this.velocityRight = velocityRight;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth() {
        health -= 1;
        System.out.println(health);
    }

    public int getEnergy(){
        return (int) energy;
    }

    public void setEnergy(float x){

        energy -= x / DataHandler.shieldLevel;
        if(energy < 0 && !energyDestroyed){
            energyDestroyed = true;
            AssetLoader.shieldBreak.play(0.5f);
        }

    }

    public boolean getEnergyDestroyed(){
        return energyDestroyed;
    }

    public boolean getHit(){
        return hit;
    }

    public void setHit(boolean x){

        hit = x;
    }

}

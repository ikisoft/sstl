package com.ikisoft.sstl.gameobjects;

import com.ikisoft.sstl.helpers.AssetLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.sstl.main.Updater;

import java.util.Random;

/**
 * Created by maxikahe on 20.5.2017.
 */

public class Spacejunk {

    private Updater updater;
    private Random random;
    private Vector2 position;
    private Vector2 fragment1;
    private Vector2 fragment2;
    private Vector2 fragment3;
    private Rectangle hitbox;
    private boolean destroyed;
    private float randomSpeed;

    public Spacejunk(float width, float height, Updater updater){

        this.updater = updater;
        random = new Random();
        position = new Vector2(random.nextInt(1000), random.nextInt(5000 - 2176) + 2176);
        fragment1 = new Vector2(position.cpy());
        fragment2 = new Vector2(position.cpy());
        fragment3 = new Vector2(position.cpy());
        hitbox = new Rectangle(position.x, position.y, width, height);
        destroyed = false;
        randomSpeed = random.nextInt(10);

    }


    public void reset(){

        position.set(random.nextInt(1000), random.nextInt(2500 - 2176) + 2176);
        fragment1.set(position.cpy());
        fragment2.set(position.cpy());
        fragment3.set(position.cpy());
        hitbox.setPosition(position.x, position.y);
        destroyed = false;
        randomSpeed = random.nextInt((int) (10 + updater.getSpeed() / 100));

    }

    public void update(float delta) {

        if(!destroyed){

            if(position.y < -256){
                reset();
            }
            position.y -= (10 + randomSpeed) * delta;



            hitbox.x = position.x;
            hitbox.y = position.y;

            fragment1.x = position.x + getHitbox().width / 2;
            fragment2.x = position.x + getHitbox().width / 2;
            fragment3.x = position.x + getHitbox().width / 2;
            fragment1.y = position.y;
            fragment2.y = position.y;
            fragment3.y = position.y;

        } else {

            position.y -= (5) * delta;
            fragment1.x += random.nextInt(5) * delta;
            fragment1.y -= random.nextInt(5) * delta;
            fragment2.x -= random.nextInt(5) * delta;
            fragment2.y -= random.nextInt(5) * delta;
            //fragment3.x = random.nextInt(5) * delta;
            fragment3.y -= random.nextInt(5) * delta;

            if(position.y < -500)reset();
        }
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

    public boolean getDestroyed(){
        return destroyed;

    }

    public void setDestroyed(){


        if(updater.getGameState() == Updater.GameState.RUNNING && !destroyed){


        }

        destroyed = true;

    }

    public Vector2 getFragment1(){
        return fragment1;
    }

    public Vector2 getFragment2(){
        return fragment2;
    }

    public Vector2 getFragment3(){
        return fragment3;
    }


}


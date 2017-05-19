package com.ikisoft.sstl.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by maxikahe on 20.5.2017.
 */

public class Spacejunk {

    private Vector2 position;
    private Rectangle hitbox;
    private Random random;


    public Spacejunk(float x, float y, float width, float height){

        position = new Vector2(x, y);
        hitbox = new Rectangle();
        hitbox.setSize(width, height);
        random = new Random();

    }

    public void update(float delta) {

        position.y -= 10 * delta;
        if(position.y < -256){
            position.y = 2176;
            position.x = random.nextInt(900);
            //position.y = random.nextInt(500 + 2176) - 2176;
            //position.y = random.nextInt(500 + 2176);
        }

        hitbox.x = position.x;
        hitbox.y = position.y;
    }

    public void reset(){
        setPosition(random.nextInt(900), 1600);
        hitbox.x = position.x;
        hitbox.y = position.y;
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


}


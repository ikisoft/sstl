package com.ikisoft.sstl.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ikisoft.sstl.main.Updater;

import java.util.Random;

/**
 * Created by Max on 22.5.2017.
 */

public class Money {

    private Updater updater;
    private Random random;
    private Vector2 position;
    private Rectangle hitbox;
    private boolean collected;
    private float randomSpeed;
    private int value;

    public Money(float width, float height, Updater updater, int value) {

        this.updater = updater;
        random = new Random();
        position = new Vector2(random.nextInt(1000) - 128, random.nextInt(6000 - 2048) + 2048);
        hitbox = new Rectangle(position.x, position.y, width, height);
        collected = false;
        randomSpeed = random.nextInt(10);
        this.value = value;
    }


    public void reset() {

        position.set(random.nextInt(1000) - 128, random.nextInt(3000 - 2048) + 2048);
        hitbox.setPosition(position.x, position.y);
        collected = false;
        randomSpeed = random.nextInt((int) (10 + updater.getSpeed() / 100));

    }

    public void update(float delta) {

        if (!collected) {
            if (position.y < -256 - random.nextInt(512)) {
                reset();
            }

            position.y -= (10 + randomSpeed) * delta;
            hitbox.x = position.x;
            hitbox.y = position.y;
        }

        if (position.y < -500 - random.nextInt(512)) reset();
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

    public boolean getCollected() {
        return collected;

    }

    public void setCollected() {
        collected = true;

    }
}




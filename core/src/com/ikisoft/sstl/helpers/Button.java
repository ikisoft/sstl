package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Button {

    private Vector2 size;

    public Button(float width, float height) {

        size = new Vector2(width, height);

    }

    public boolean isDown(int x, int y, float posx, float posy) {

        if (x > posx - (size.x / 2) && x < posx + (size.x / 2)) {
            if (y > posy - (size.y / 2) && y < posy + (size.y / 2)) {
                return true;
            }
        }
        return false;
    }
}



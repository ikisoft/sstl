package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion
            spacecraft, spacecraftLeft, spacecraftRight,
            asteroid, planet,
            bg,
            junk, junk2, junk3, junk4, junk5, junk6, junk7, junk8,
            healthFrame, health,
            fragment1, fragment2, fragment3;

    public static Sprite
            cow, fish, can, healthBarFrame, healthBar;

    public static BitmapFont font;



    public static void load(){

        texture = new Texture(Gdx.files.internal("textures/textureatlas.png"));
        spacecraft = new TextureRegion(texture, 0, 0, 256, 256);
        spacecraftLeft = new TextureRegion(texture, 256, 0, 256, 256);
        spacecraftRight = new TextureRegion(texture, 512, 0, 256, 256);
        asteroid = new TextureRegion(texture, 0, 256, 256, 256);
        planet = new TextureRegion(texture, 256, 256, 256, 256);
        bg = new TextureRegion(texture, 0, 512, 256, 256);
        //cow
        junk = new TextureRegion(texture, 0, 768, 256, 256);
        cow = new Sprite(junk);
        //cow.setOrigin(256/2, 256/2);
        junk2 = new TextureRegion(texture, 256, 768, 256, 256);
        fish = new Sprite(junk2);
        junk3 = new TextureRegion(texture, 512, 768, 256, 256);
        can = new Sprite(junk3);
        junk4 = new TextureRegion(texture, 768, 768, 256, 256);
        junk5 = new TextureRegion(texture, 1024, 768, 256, 256);
        junk6 = new TextureRegion(texture, 1280, 768, 256, 256);
        junk7 = new TextureRegion(texture, 1536, 768, 256, 256);
        junk8 = new TextureRegion(texture, 1792, 768, 256, 256);
        healthFrame = new TextureRegion(texture, 0, 1024, 256, 256);
        healthBarFrame = new Sprite(healthFrame);
        health = new TextureRegion(texture, 256, 1024, 256, 256);
        healthBar = new Sprite(health);
        fragment1 = new TextureRegion(texture, 0, 1536, 256, 256);
        fragment2 = new TextureRegion(texture, 256, 1536, 256, 256);
        fragment3 = new TextureRegion(texture, 512, 1536, 256, 256);








        font = new BitmapFont(Gdx.files.internal("fonts/ehs.fnt"));
        font.getData().setScale(2f, 2f);
        font.setColor(0.141f, 0.848f, 0.407f, 1f);

    }

    public static void dispose(){
        texture.dispose();
    }
}



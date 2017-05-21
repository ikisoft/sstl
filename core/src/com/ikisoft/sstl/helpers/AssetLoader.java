package com.ikisoft.sstl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class AssetLoader {


    public static Animation<TextureRegion> wireframeAnimation;

    public static Texture texture;
    public static TextureRegion
            spacecraft, spacecraftLeft, spacecraftRight,
            asteroid, planet,
            bg,
            junk, junk2, junk3, junk4, junk5, junk6, junk7, junk8,
            healthFrame, health,
            fragment1, fragment2, fragment3,
            wireframe1, wireframe2, wireframe3, wireframe4,
            wireframe5, wireframe6, wireframe7, wireframe8,
            speedTier1, speedTier1Left, speedTier1Right,
            speedTier2, speedTier2Left, speedTier2Right,
            armorTier1, armorTier1Left, armorTier1Right,
            armorTier2, armorTier2Left, armorTier2Right,

            speedUp1, speedUp2, armorUp1, armorUp2, armorUp3,
            soundMuted, musicMuted;

    public static Sprite
            cow, fish, can, healthBarFrame, healthBar;

    public static BitmapFont font, fontSmall;

    public static Music theme;



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

        wireframe1 = new TextureRegion(texture, 0, 1796, 256, 256);
        wireframe2 = new TextureRegion(texture, 256, 1796, 256, 256);
        wireframe3 = new TextureRegion(texture, 512, 1796, 256, 256);
        wireframe4 = new TextureRegion(texture, 768, 1796, 256, 256);
        wireframe5 = new TextureRegion(texture, 1024, 1796, 256, 256);
        wireframe6 = new TextureRegion(texture, 1280, 1796, 256, 256);
        wireframe7 = new TextureRegion(texture, 1536, 1796, 256, 256);
        wireframe8 = new TextureRegion(texture, 1792, 1796, 256, 256);

        TextureRegion[] wireFrame = {wireframe1, wireframe2, wireframe3,
        wireframe4, wireframe5, wireframe6, wireframe7, wireframe8};
        wireframeAnimation = new Animation(0.3f, wireFrame);
        wireframeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        speedTier1 = new TextureRegion(texture, 0, 2048, 256, 256);
        speedTier1Left = new TextureRegion(texture, 256, 2048, 256, 256);
        speedTier1Right = new TextureRegion(texture, 512, 2048, 256, 256);
        speedTier2 = new TextureRegion(texture, 768, 2048, 256, 256);
        speedTier2Left = new TextureRegion(texture, 1024, 2048, 256, 256);
        speedTier2Right = new TextureRegion(texture, 1280, 2048, 256, 256);

        armorTier1 = new TextureRegion(texture, 768, 0, 256, 256);
        armorTier1Left = new TextureRegion(texture, 1024, 0, 256, 256);
        armorTier1Right = new TextureRegion(texture, 1280, 0, 256, 256);

        armorTier2 = new TextureRegion(texture, 2304, 0, 256, 256);
        armorTier2Left = new TextureRegion(texture, 2560, 0, 256, 256);
        armorTier2Right = new TextureRegion(texture, 2816, 0, 256, 256);

        speedUp1 = new TextureRegion(texture, 0, 2304, 256, 256);
        speedUp2 = new TextureRegion(texture, 256, 2304, 256, 256);
        armorUp1 = new TextureRegion(texture, 512, 2304, 256, 256);
        armorUp2 = new TextureRegion(texture, 768, 2304, 256, 256);
        armorUp3 = new TextureRegion(texture, 1024, 2304, 256, 256);










        font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
        font.getData().setScale(2f, 2f);
        font.setColor(0.141f, 0.848f, 0.407f, 1f);
        fontSmall = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
        fontSmall.getData().setScale(1.5f, 1.5f);
        fontSmall.setColor(0.141f, 0.848f, 0.407f, 1f);


        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/hello_world_msu.mp3"));

        theme.setVolume(0.7f);
        theme.setLooping(true);
        if (!DataHandler.musicMuted) theme.play();


    }

    public static void dispose(){

        texture.dispose();
        theme.dispose();
        font.dispose();
        fontSmall.dispose();
    }
}



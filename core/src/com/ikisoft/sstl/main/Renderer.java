package com.ikisoft.sstl.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.AssetLoader;

import java.util.Random;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Renderer {

    private Random random;
    private Updater updater;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private static final float VIRTUAL_WIDTH = 1080;
    private static final float VIRTUAL_HEIGHT = 1920;
    private static OrthographicCamera camera;
    private float runTime, rotation, rotation2, r, g, b, r2, g2, b2, r3, g3, b3, glyphWidth;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private TextureRegion spacecraftTexture;
    private Vector2 planetPosition, asteroidPosition;
    private boolean devEnabled;
    private GlyphLayout glyphLayout;
    private String logo, info, shop, options, gameover;

    public Renderer(Updater updater){

        devEnabled = false;


        random = new Random();
        this.updater = updater;
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        rotation2 = 360;
        planetPosition = new Vector2(300, 4000);
        asteroidPosition = new Vector2(800, random.nextInt(3000));
        r = random.nextFloat();
        g = random.nextFloat();
        b = random.nextFloat();
        r2 = random.nextFloat();
        g2 = random.nextFloat();
        b2 = random.nextFloat();
        r3 = random.nextFloat();
        if(r3 > 0.25f)r3 = 0.25f;
        g3 = random.nextFloat();
        if(g3 > 0.25f)g3 = 0.25f;
        b3 = random.nextFloat();
        if(b3 > 0.25f)g3 = 0.25f;



        glyphLayout = new GlyphLayout();
        logo = "   (Slightly)\nSlower Than Light";
        info = "just some info lol";
        shop = "wow look at this\nfucking shop";
        options = "here are some\noptions!";
        gameover = "oh dear,\nyou ran into\nsome junk...";




    }

    public void render(){


        switch (updater.getGameState()){
            case START:
                renderStart();
                break;
            case MAINMENU:
                renderMainmenu();
                break;
            case RUNNING:
                renderRunning();
                break;
            case PAUSED:
                renderPaused();
                break;
            case GAMEOVER:
                renderGameover();
                break;
            case OPTIONS:
                renderOptions();
                break;
            case SHOP:
                renderShop();
                break;
            case INFO:
                renderInfo();
            default:
                break;
        }





    }

    private void renderStart(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, logo);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2, updater.getLogo().y);
        glyphLayout.setText(AssetLoader.font, "start");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 550);



        glyphLayout.setText(AssetLoader.font, "shop");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 700);

        glyphLayout.setText(AssetLoader.font, "info");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 850);

        glyphLayout.setText(AssetLoader.font, "options");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 1000);

        batch.end();


    }

    private void renderMainmenu(){

    }

    private void renderRunning(){

        runTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);

        handleCalc();

        spacecraft = updater.getSpacecraft();
        spacejunk = updater.getSpacejunk();
        spacejunk2 = updater.getSpacejunk2();
        spacejunk3 = updater.getSpacejunk3();
        spacejunk4 = updater.getSpacejunk4();
        spacejunk5 = updater.getSpacejunk5();

        batch.begin();
        //drawBackground();
        batch.enableBlending();
        batch.setColor(r, g, b, 1);
        drawPlanet();
        batch.setColor(r2, g2, b2, 1);
        drawAsteroid();
        batch.setColor(1, 1, 1, 1);

        //spacecraft
        drawSpacecraft();

        //spacejunk
        //cow
        batch.setColor(r3 + 0.25f, g3 + 0.1f, b3 + 0.5f, 1);
        drawSpacejunk();
        //fish
        batch.setColor(1, 1, 1, 1);
        drawSpacejunk2();
        drawSpacejunk3();
        drawSpacejunk4();
        drawSpacejunk5();

        //ui

        drawHealth();

        /*if(updater.getSpacecraft().getHealth() < 2){

        glyphLayout.setText(AssetLoader.font, "HULL CONDITION\n  CRITICAL");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                VIRTUAL_HEIGHT/2);

        }*/

        if(devEnabled)AssetLoader.font.draw(batch, "Distance: " + updater.getDistance(), 50, 1800);

        System.out.println(updater.getDistance());
        batch.end();
        shapeRenderer.end();


    }

    private void renderPaused(){

    }

    private void renderInfo() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, info);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2, updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "BACK");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 1000);

        batch.end();


    }

    private void renderShop() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, shop);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2, updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "PLAY");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 1000);

        batch.end();
    }

    private void renderGameover(){

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, gameover);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "Score: " + updater.getDistance());
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 300);

        glyphLayout.setText(AssetLoader.font, "SHOP");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 700);

        glyphLayout.setText(AssetLoader.font, "OPTIONS");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 850);

        glyphLayout.setText(AssetLoader.font, "PLAY");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 1000);

        batch.end();

    }

    private void renderOptions(){

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, options);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2, updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "PLAY");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth)/2,
                updater.getLogo().y - 1000);

        batch.end();


    }

    private void drawBackground() {

        for(int i = 1; i < 1080; i += 256){
            for(int j = 1; j < 1920; j += 256){

                batch.draw(AssetLoader.bg, i, j);

            }
        }
    }

    private void drawSpacecraft() {

        spacecraftTexture = AssetLoader.spacecraft;

        if(spacecraft.getMovingLeft()){
            spacecraftTexture = AssetLoader.spacecraftLeft;
        } else if(spacecraft.getMovingRight()){
            spacecraftTexture = AssetLoader.spacecraftRight;
        }

        batch.draw(spacecraftTexture,
                spacecraft.getPosition().x - 64,
                spacecraft.getPosition().y

                //,
                //spacecraft.getHitbox().width,
                //spacecraft.getHitbox().height
        );

        if(devEnabled){

            shapeRenderer.rect(
                    spacecraft.getPosition().x,
                    spacecraft.getPosition().y,
                    spacecraft.getHitbox().width,
                    spacecraft.getHitbox().height
            );
        }
    }

    //COW
    private void drawSpacejunk(){

        //batch.draw(cow, 100, 100, 128, 128, 256, 256, 1, 1, rotation, false);

        batch.draw(AssetLoader.cow,
                spacejunk.getPosition().x,
                spacejunk.getPosition().y,
                spacejunk.getHitbox().width / 2,
                spacejunk.getHitbox().height / 2,
                spacejunk.getHitbox().width,
                spacejunk.getHitbox().height,
                1, 1, rotation2, false
        );

        //TODO make spinning if time
        if(spacejunk.getDestroyed()){
            batch.draw(AssetLoader.fragment1,
                    spacejunk.getFragment1().x,
                    spacejunk.getFragment1().y,
                    64, 64);
            batch.draw(AssetLoader.fragment2,
                    spacejunk.getFragment2().x,
                    spacejunk.getFragment2().y,
                    128, 128);
            batch.draw(AssetLoader.fragment3,
                    spacejunk.getFragment3().x,
                    spacejunk.getFragment3().y,
                    128, 128);
        }

        //DEV
        if(devEnabled){

            //spacejunk1
            shapeRenderer.rect(
                    spacejunk.getPosition().x,
                    spacejunk.getPosition().y,
                    spacejunk.getHitbox().width,
                    spacejunk.getHitbox().height);

            //fragments
            shapeRenderer.rect(
                    spacejunk.getFragment1().x,
                    spacejunk.getFragment1().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk.getFragment2().x,
                    spacejunk.getFragment2().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk.getFragment3().x,
                    spacejunk.getFragment3().y,
                    1,
                    64);
        }


    }

    //FISH
    private void drawSpacejunk2() {

        batch.draw(AssetLoader.fish,
                spacejunk2.getPosition().x,
                spacejunk2.getPosition().y,
                spacejunk2.getHitbox().width / 2,
                spacejunk2.getHitbox().height / 2,
                spacejunk2.getHitbox().width,
                spacejunk2.getHitbox().height,
                1, 1, rotation2, true
        );

        if(spacejunk2.getDestroyed()){
            batch.draw(AssetLoader.fragment1,
                    spacejunk2.getFragment1().x,
                    spacejunk2.getFragment1().y,
                    32, 32);
            batch.draw(AssetLoader.fragment2,
                    spacejunk2.getFragment2().x,
                    spacejunk2.getFragment2().y,
                    32, 32);
            batch.draw(AssetLoader.fragment3,
                    spacejunk2.getFragment3().x,
                    spacejunk2.getFragment3().y,
                    16, 16);
        }

        //DEV
        if(devEnabled){

            shapeRenderer.rect(
                    spacejunk2.getPosition().x,
                    spacejunk2.getPosition().y,
                    spacejunk2.getHitbox().width,
                    spacejunk2.getHitbox().height);
            //fragments
            shapeRenderer.rect(
                    spacejunk2.getFragment1().x,
                    spacejunk2.getFragment1().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk2.getFragment2().x,
                    spacejunk2.getFragment2().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk2.getFragment3().x,
                    spacejunk2.getFragment3().y,
                    1,
                    64);
        }


    }

    //can
    private void drawSpacejunk3(){

        batch.draw(AssetLoader.can,
                spacejunk3.getPosition().x,
                spacejunk3.getPosition().y,
                spacejunk3.getHitbox().width / 2,
                spacejunk3.getHitbox().height / 2,
                spacejunk3.getHitbox().width,
                spacejunk3.getHitbox().height,
                1, 1, rotation2, true
        );

        if(spacejunk3.getDestroyed()){
            batch.draw(AssetLoader.fragment1,
                    spacejunk3.getFragment1().x,
                    spacejunk3.getFragment1().y,
                    32, 32);
            batch.draw(AssetLoader.fragment2,
                    spacejunk3.getFragment2().x,
                    spacejunk3.getFragment2().y,
                    32, 32);
            batch.draw(AssetLoader.fragment3,
                    spacejunk3.getFragment3().x,
                    spacejunk3.getFragment3().y,
                    32, 32);
        }

        if(devEnabled){

            shapeRenderer.rect(
                    spacejunk3.getPosition().x,
                    spacejunk3.getPosition().y,
                    spacejunk3.getHitbox().width,
                    spacejunk3.getHitbox().height);
            //fragments
            shapeRenderer.rect(
                    spacejunk3.getFragment1().x,
                    spacejunk3.getFragment1().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk3.getFragment2().x,
                    spacejunk3.getFragment2().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk3.getFragment3().x,
                    spacejunk3.getFragment3().y,
                    1,
                    64);
        }

    }
    //CAN
    private void drawSpacejunk4(){

        batch.draw(AssetLoader.junk4,
                spacejunk4.getPosition().x,
                spacejunk4.getPosition().y,
                spacejunk4.getHitbox().width,
                spacejunk4.getHitbox().height
        );

        if(spacejunk4.getDestroyed()){
            batch.draw(AssetLoader.fragment1,
                    spacejunk4.getFragment1().x,
                    spacejunk4.getFragment1().y,
                    32, 32);
            batch.draw(AssetLoader.fragment2,
                    spacejunk4.getFragment2().x,
                    spacejunk4.getFragment2().y,
                    8, 8);
            batch.draw(AssetLoader.fragment3,
                    spacejunk4.getFragment3().x,
                    spacejunk4.getFragment3().y,
                    16, 16);
        }

        //DEV

        if(devEnabled){

            shapeRenderer.rect(
                    spacejunk4.getPosition().x,
                    spacejunk4.getPosition().y,
                    spacejunk4.getHitbox().width,
                    spacejunk4.getHitbox().height);
            //fragments
            shapeRenderer.rect(
                    spacejunk4.getFragment1().x,
                    spacejunk4.getFragment1().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk4.getFragment2().x,
                    spacejunk4.getFragment2().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk4.getFragment3().x,
                    spacejunk4.getFragment3().y,
                    1,
                    64);
        }

    }

    //TIRE
    private void drawSpacejunk5(){

        batch.draw(AssetLoader.junk5,
                spacejunk5.getPosition().x,
                spacejunk5.getPosition().y,
                spacejunk5.getHitbox().width,
                spacejunk5.getHitbox().height
        );

        if(spacejunk5.getDestroyed()){
            batch.setColor(0.5f, 0.5f, 0.5f, 1);
            batch.draw(AssetLoader.fragment1,
                    spacejunk5.getFragment1().x,
                    spacejunk5.getFragment1().y,
                    64, 64);
            batch.draw(AssetLoader.fragment2,
                    spacejunk5.getFragment2().x,
                    spacejunk5.getFragment2().y,
                    128, 128);
            batch.draw(AssetLoader.fragment3,
                    spacejunk5.getFragment3().x,
                    spacejunk5.getFragment3().y,
                    32, 32);
            batch.setColor(1,1,1,1);
        }

        //DEV
        if(devEnabled){

            shapeRenderer.rect(
                    spacejunk5.getPosition().x,
                    spacejunk5.getPosition().y,
                    spacejunk5.getHitbox().width,
                    spacejunk5.getHitbox().height);
            //fragments
            shapeRenderer.rect(
                    spacejunk5.getFragment1().x,
                    spacejunk5.getFragment1().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk5.getFragment2().x,
                    spacejunk5.getFragment2().y,
                    1,
                    64);
            shapeRenderer.rect(
                    spacejunk5.getFragment3().x,
                    spacejunk5.getFragment3().y,
                    1,
                    64);
        }

    }

    public void drawHealth(){

        for(int i = 220; i < 860 - 1; i += 64){
            batch.draw(AssetLoader.healthBarFrame, i, 256, 64, 64);

            }

        for(int i = 0; i < 640 * (updater.getSpacecraft().getHealth() * 0.1) - 1; i += 64){
            batch.draw(AssetLoader.healthBar, i + 220, 256, 64, 64);


        }



    }

    private void drawAsteroid() {
        batch.draw(AssetLoader.asteroid, asteroidPosition.x, asteroidPosition.y);
    }

    private void drawPlanet() {
        batch.draw(AssetLoader.planet, planetPosition.x, planetPosition.y);
    }

    private void handleCalc(){

        planetPosition.y += -0.5;
        planetPosition.x += 0.1;
        asteroidPosition.y += -1;
        asteroidPosition.x -= 0.3;
        if(planetPosition.y < -300){
            planetPosition.y = random.nextInt(3000- 2500) + 2500;
            planetPosition.x = random.nextInt(1080);

            r = random.nextFloat();
            if(r > 0.25f)r = 0.25f;
            g = random.nextFloat();
            if(g > 0.25f)g = 0.25f;
            b = random.nextFloat();
            if(b > 0.25f)b = 0.25f;
        }
        if(asteroidPosition.y < -300){
            asteroidPosition.y = random.nextInt(3000 - 2500) + 2500;

            r2 = random.nextFloat();
            if(r2 > 0.25f)r2 = 0.25f;
            g2 = random.nextFloat();
            if(g2 > 0.25f)g2 = 0.25f;
            b2 = random.nextFloat();
            if(b2 > 0.25f)b2 = 0.25f;

        }

        rotation2 -= 0.5;
        if(rotation2 < 0f)rotation2 = 360f;

    }
}


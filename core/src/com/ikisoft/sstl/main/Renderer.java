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
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Renderer {

    private long startTime = 0;
    private Random random;
    private Updater updater;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private static final float VIRTUAL_WIDTH = 1080;
    private static final float VIRTUAL_HEIGHT = 1920;
    private static OrthographicCamera camera;
    private float rotation, rotation2, glyphWidth, glyphHeight,
            bgSpeed;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private TextureRegion spacecraftTexture;
    private Vector2 planetPosition, asteroidPosition;
    private boolean devEnabled, gameover, warningSwitch, timeReset;
    private GlyphLayout glyphLayout;
    private String logoText, infoText, shopText, optionsText, gameoverText, armorText,
            thrusterText, kineticText, energyShieldText;
    private int timer, armor, speed, hudHeight, particlePosY, particlePosX, speedFrameY;
    private ArrayList<Float> rc;

    public Renderer(Updater updater) {

        speedFrameY = 100;
        bgSpeed = 0;
        hudHeight = 32;
        particlePosY = 0;
        particlePosX = 0;
        devEnabled = false;
        gameover = false;
        startTime = TimeUtils.nanoTime();
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
        rotation = 0;
        planetPosition = new Vector2(300, random.nextInt(1000));
        asteroidPosition = new Vector2(800, random.nextInt(1000));
        rc = new ArrayList();

        for (int i = 0; i < 6; i++) {
            rc.add(i, (random.nextFloat() + 0.5f) - 0.25f);
        }


        timer = 0;
        warningSwitch = false;


        glyphLayout = new GlyphLayout();
        logoText = "   (Slightly)\nSlower Than Light";
        infoText = "GFH JAM 2017\n" +
                "  ikisoft";
        shopText = "ARMOR: " + DataHandler.healthLevel + "\n" +
                "THRUSTERS: " + DataHandler.speedLevel + "\n" +
                "KINETIC BARRIER: " + DataHandler.kineticBarrierLevel + "\n" +
                "ENERGY SHIELD: " + DataHandler.shieldLevel + "\n\n" +
        "MONEY: " + DataHandler.money + "$\n" +
        "SCRAP METAL: " + DataHandler.scrap;
        optionsText = "here are some\noptionsText!";
        gameoverText = "oh dear,\nyou ran into\nsome junk...";
        armorText = "The armor plating of your spaceship\n" +
                "determines how many hits you can\n" +
                "take before your ship breaks.";
        thrusterText = "Thrusters increase your horizontal\nspeed" +
                " but they also increase\ndrifting.";
        kineticText = "Kinetic barrier reduces the speed\n" +
                "penalty when hitting something.";
        energyShieldText = "Energy shield will protect your\nships" +
                " hull from damaging impacts.\n" +
                "Needs time to recharge.";
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (updater.getGameState()) {
            case START:
                renderStart(runTime);
                break;
            case MAINMENU:
                renderMainmenu();
                break;
            case RUNNING:
                timeReset = false;
                timer++;
                if (timer > 120) timer = 0;
                gameover = false;
                renderRunning(runTime);
                break;
            case PAUSED:
                renderPaused();
                break;
            case GAMEOVER:
                resetTimer();
                timer++;
                particlePosY = 0;

                if (timer >= 300) timer = 300;

                if ((timer % 300) == 0) {
                    renderGameover(runTime);
                } else {
                    renderRunning(runTime);
                }


                break;
            case OPTIONS:
                renderOptions(runTime);
                break;
            case SHOP:
                renderShop(runTime);
                break;
            case INFO:
                renderInfo();
            default:
                break;
        }


    }

    private void resetTimer() {

        if (!timeReset) {
            timer = 0;
            timeReset = true;
        }

    }

    private void renderStart(float runTime) {


        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, logoText);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2, updater.getLogo().y);
        glyphLayout.setText(AssetLoader.font, "start");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 550);


        glyphLayout.setText(AssetLoader.font, "shop");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 700);

        glyphLayout.setText(AssetLoader.font, "info");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 850);

        glyphLayout.setText(AssetLoader.font, "options");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 1000);

        batch.draw(AssetLoader.wireframeAnimation.getKeyFrame(runTime),
                540 - 256, updater.getLogo().y - 550, 512, 512);

        batch.end();


    }

    private void renderMainmenu() {

    }

    private void renderRunning(float runTime) {


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


        batch.enableBlending();

        batch.setColor(rc.get(0), rc.get(1), rc.get(2), 1);
        drawPlanet();
        batch.setColor(rc.get(3), rc.get(4), rc.get(5), 1);
        drawAsteroid();
        batch.setColor(1, 1, 1, 1);

        //spacecraft
        drawSpacecraft();

        //spacejunk
        //cow
        batch.setColor(rc.get(0), rc.get(1), rc.get(2), 1);
        drawSpacejunk();
        //fish
        batch.setColor(1, 1, 1, 1);

        drawMoney();
        drawCrates();

        drawSpacejunk2();
        drawSpacejunk3();
        drawSpacejunk4();
        drawSpacejunk5();

        //drawParticleEffect();
        drawBackground();
        //ui

        drawHealth();
        drawSpeed();


        if (spacecraft.getHealth() < 2) {
            if ((timer % 60) == 0) {
                warningSwitch = true;
            }
            if (warningSwitch) {
                drawWarning();
            }
            if ((timer % 120) == 0) {
                warningSwitch = false;
            }
        }


        if (updater.getDevEnabled()) {
            AssetLoader.font.draw(batch, "Distance: " + updater.getDistance(), 50, 1800);
            AssetLoader.font.draw(batch, "Speed: " + updater.getSpeed(), 50, 1650);
        }


        batch.end();
        shapeRenderer.end();


    }

    private void drawParticleEffect() {

        bgSpeed -= (float) updater.getSpeed() / 10;
        if (bgSpeed < -256) bgSpeed = 0;

        for (int i = 1; i < 1080; i += 256) {
            for (int j = 1; j < 1920; j += 256) {

                batch.draw(AssetLoader.particleEffect, i, j + bgSpeed);

            }

        }
    }

    private void renderPaused() {

    }

    private void renderInfo() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, infoText);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2, updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "COMING TO GOOGLE PLAY\n" +
                "When it's ready...");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 175);

        glyphLayout.setText(AssetLoader.font, "MUSIC & PROGRAMMING\n" +
                "       IKIS");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 500);

        glyphLayout.setText(AssetLoader.font, "GRAPHIC DESIGNER\n" +
                "  EELIS OTSAMO");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 750);

        glyphLayout.setText(AssetLoader.font, "BACK");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 1000);

        batch.end();


    }

    private void renderShop(float runTime) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();
        batch.setColor(1, 1, 1, 1);
        //batch.draw(AssetLoader.wireframeAnimation.getKeyFrame(runTime), 568, 1300, 512, 512);
        batch.draw(AssetLoader.wireframe6, 568, 1300, 512, 512);

        glyphLayout.setText(AssetLoader.fontMedium, shopText);
        glyphWidth = glyphLayout.width;
        AssetLoader.fontMedium.draw(batch, glyphLayout, 40, updater.getLogo().y + 120);

        /*glyphLayout.setText(AssetLoader.fontMedium, DataHandler.money + "$");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y);*/

        /*glyphLayout.setText(AssetLoader.font, "OPTIONS");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 850);
*/
        glyphLayout.setText(AssetLoader.font, "BACK");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 1400);


        //ARMOR

        batch.setColor(0.5f, 0.5f, 0.5f, 1);
        batch.draw(AssetLoader.armorUp3, 35, updater.getLogo().y - 800);
        /*glyphLayout.setText(AssetLoader.fontSmall, "Upgrade\narmor\n");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontSmall.draw(batch, glyphLayout, 75,
                updater.getLogo().y - 830);*/

        if (DataHandler.upgradeSelected == 1) {

            batch.setColor(1, 1, 1, 1);
            batch.draw(AssetLoader.armorUp3, 35, updater.getLogo().y - 800);
            glyphLayout.setText(AssetLoader.fontMedium, armorText);
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 900);

            //healthframe and health are just used to render, no reference from now on
            for (int i = 220; i < 860 - 1; i += 64) {
                batch.draw(AssetLoader.healthFrame, i, 256, 64, 64);
            }
            for (int i = 0; i < 640 * (DataHandler.healthLevel * 0.1) - 1; i += 64) {
                batch.draw(AssetLoader.health, i + 220, 256, 64, 64);
            }

            glyphLayout.setText(AssetLoader.fontMedium, "UPGRADE");
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 1150);

        }

        //THRUSTER
        batch.setColor(0.5f, 0.5f, 0.5f, 1);
        batch.draw(AssetLoader.speedUp1, 280, updater.getLogo().y - 800);
        /*glyphLayout.setText(AssetLoader.fontSmall, "Upgrade\nthrusters");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontSmall.draw(batch, glyphLayout, 310,
                updater.getLogo().y - 830);*/

        if (DataHandler.upgradeSelected == 2) {

            batch.setColor(1, 1, 1, 1);
            batch.draw(AssetLoader.speedUp1, 280, updater.getLogo().y - 800);
            glyphLayout.setText(AssetLoader.fontMedium, thrusterText);
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 900);

            for (int i = 220; i < 860 - 1; i += 64) {
                batch.draw(AssetLoader.healthFrame, i, 256, 64, 64);
            }
            for (int i = 0; i < 640 * (DataHandler.speedLevel * 0.1) - 1; i += 64) {
                batch.draw(AssetLoader.health, i + 220, 256, 64, 64);
            }

            glyphLayout.setText(AssetLoader.fontMedium, "UPGRADE");
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 1150);
        }

        //KINETIC
        batch.setColor(0.5f, 0.5f, 0.5f, 1);
        batch.draw(AssetLoader.kineticShield, 545, updater.getLogo().y - 800);
        /*glyphLayout.setText(AssetLoader.fontSmall, "Upgrade\nkinetic\nbarrier");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontSmall.draw(batch, glyphLayout, 600,
                updater.getLogo().y - 830);*/

        if (DataHandler.upgradeSelected == 3) {

            batch.setColor(1, 1, 1, 1);
            batch.draw(AssetLoader.kineticShield, 545, updater.getLogo().y - 800);
            glyphLayout.setText(AssetLoader.fontMedium, kineticText);
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 900);

            for (int i = 220; i < 860 - 1; i += 64) {
                batch.draw(AssetLoader.healthFrame, i, 256, 64, 64);
            }
            for (int i = 0; i < 640 * (DataHandler.kineticBarrierLevel * 0.1) - 1; i += 64) {
                batch.draw(AssetLoader.health, i + 220, 256, 64, 64);
            }

            glyphLayout.setText(AssetLoader.fontMedium, "UPGRADE");
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 1150);

        }

        //SHIELD
        batch.setColor(0.5f, 0.5f, 0.5f, 1);
        batch.draw(AssetLoader.barrier, 813, updater.getLogo().y - 800);
        /*glyphLayout.setText(AssetLoader.fontSmall, "Upgrade\nEnergy\nShield");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontSmall.draw(batch, glyphLayout, 868,
                updater.getLogo().y - 830);*/

        if (DataHandler.upgradeSelected == 4) {

            batch.setColor(1, 1, 1, 1);
            batch.draw(AssetLoader.barrier, 813, updater.getLogo().y - 800);
            glyphLayout.setText(AssetLoader.fontMedium, energyShieldText);
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 900);

            for (int i = 220; i < 860 - 1; i += 64) {
                batch.draw(AssetLoader.healthFrame, i, 256, 64, 64);
            }
            for (int i = 0; i < 640 * (DataHandler.shieldLevel * 0.1) - 1; i += 64) {
                batch.draw(AssetLoader.health, i + 220, 256, 64, 64);
            }

            glyphLayout.setText(AssetLoader.fontMedium, "UPGRADE");
            glyphWidth = glyphLayout.width;
            AssetLoader.fontMedium.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                    updater.getLogo().y - 1150);

        }


        batch.end();
    }

    private void renderGameover(float runTime) {

        AssetLoader.font.setColor(0.141f, 0.848f, 0.407f, 1f);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, gameoverText);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y);

        glyphLayout.setText(AssetLoader.fontSmall, DataHandler.money + "$");
        glyphWidth = glyphLayout.width;
        AssetLoader.fontSmall.draw(batch, glyphLayout, glyphWidth,
                updater.getLogo().y - 1400);

        glyphLayout.setText(AssetLoader.font, "Distance: " + updater.getDistance());
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 300);

        glyphLayout.setText(AssetLoader.font, "Money: " + updater.getMoneyThisRun());
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 400);

        glyphLayout.setText(AssetLoader.font, "SHOP");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 700);

        glyphLayout.setText(AssetLoader.font, "OPTIONS");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 850);

        glyphLayout.setText(AssetLoader.font, "PLAY");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 1000);

        batch.end();

    }

    private void renderOptions(float runTime) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.enableBlending();

        glyphLayout.setText(AssetLoader.font, optionsText);
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2, updater.getLogo().y);

        glyphLayout.setText(AssetLoader.font, "PLAY");
        glyphWidth = glyphLayout.width;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                updater.getLogo().y - 1000);

        batch.end();


    }

    private void drawBackground() {


        bgSpeed -= 10 + (float) updater.getSpeed() / 100;
        if (bgSpeed < -256) bgSpeed = 0;

        for (int i = 1; i < 1080; i += 256) {
            for (int j = 1; j < 2176; j += 256) {

                batch.draw(AssetLoader.bg, i, j + bgSpeed);

            }

        }

    }

    private void drawSpacecraft() {

        drawAfterburner();

        armor = DataHandler.healthLevel;
        speed = DataHandler.speedLevel;

        if (armor >= 3 && armor < 5) {
            spacecraftTexture = AssetLoader.spacecraft;

            if (spacecraft.getMovingLeft()) {
                drawMovingLeft();

            } else if (spacecraft.getMovingRight()) {
                drawMovingRight();

            }

        } else if (armor >= 5 && armor < 10) {

            spacecraftTexture = AssetLoader.armorTier1;

            if (spacecraft.getMovingLeft()) {
                spacecraftTexture = AssetLoader.armorTier1Left;
            } else if (spacecraft.getMovingRight()) {
                spacecraftTexture = AssetLoader.armorTier1Right;
            }

        } else if (armor == 10) {

            spacecraftTexture = AssetLoader.armorTier2;

            if (spacecraft.getMovingLeft()) {
                spacecraftTexture = AssetLoader.armorTier2Left;
            } else if (spacecraft.getMovingRight()) {
                spacecraftTexture = AssetLoader.armorTier2Right;
            }

        }


        batch.draw(spacecraftTexture,
                spacecraft.getPosition().x - 64,
                spacecraft.getPosition().y - 32

                //,
                //spacecraft.getHitbox().width,
                //spacecraft.getHitbox().height
        );


        if (updater.getDevEnabled()) {

            shapeRenderer.rect(
                    spacecraft.getPosition().x,
                    spacecraft.getPosition().y,
                    spacecraft.getHitbox().width,
                    spacecraft.getHitbox().height
            );
        }
    }

    private void drawMovingLeft() {
        spacecraftTexture = AssetLoader.spacecraftLeft;
        batch.draw(AssetLoader.speedLine,
                spacecraft.getPosition().x - 32,
                spacecraft.getPosition().y - 25,
                2, 75);


    }

    private void drawMovingRight() {
        spacecraftTexture = AssetLoader.spacecraftRight;
        batch.draw(AssetLoader.speedLine, spacecraft.getPosition().x + 153,
                spacecraft.getPosition().y - 25,
                2, 75);
    }

    private void drawAfterburner() {

        //afterburner
        //particles

        if (spacecraft.getHealth() != 0) {

            batch.enableBlending();

            batch.draw(AssetLoader.speedLine, spacecraft.getPosition().x + 153,
                    spacecraft.getPosition().y - 200);
            batch.draw(AssetLoader.speedLine, spacecraft.getPosition().x - 35,
                    spacecraft.getPosition().y - 200);


            //AFTERBURNER
            batch.draw(AssetLoader.afterburner, spacecraft.getPosition().x - 64,
                    spacecraft.getPosition().y - 180);


            batch.end();
            shapeRenderer.end();

            //START
            particlePosY += 10;
            if (particlePosY > 250) {
                particlePosY = random.nextInt(100);
                particlePosX = random.nextInt(60);
            }

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setColor(0, 0.749f, 1, 1f - ((float) particlePosY / 250));
            shapeRenderer.rect(spacecraft.getPosition().x + 90 - particlePosX,
                    spacecraft.getPosition().y - particlePosY + 50,
                    16, 16);

            shapeRenderer.rect(spacecraft.getPosition().x + 90 - particlePosX,
                    spacecraft.getPosition().y - particlePosY,
                    8, 8);
            //END
            shapeRenderer.end();
            batch.begin();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
        }

    }

    //COW
    private void drawSpacejunk() {

        //batch.draw(cow, 100, 100, 128, 128, 256, 256, 1, 1, rotation, false);

        batch.draw(AssetLoader.cow,
                spacejunk.getPosition().x - 50,
                spacejunk.getPosition().y,
                (spacejunk.getHitbox().width + 100) / 2,
                (spacejunk.getHitbox().height + 100) / 2,
                spacejunk.getHitbox().width + 100,
                spacejunk.getHitbox().height + 100,
                1, 1, rotation2, false
        );

        //TODO make spinning if time
        if (spacejunk.getDestroyed()) {
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
        if (updater.getDevEnabled()) {

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
                spacejunk2.getHitbox().width + 50,
                spacejunk2.getHitbox().height + 50,
                1, 1, 114, true
        );

        if (spacejunk2.getDestroyed()) {
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
        if (updater.getDevEnabled()) {

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
    private void drawSpacejunk3() {

        batch.draw(AssetLoader.can,
                spacejunk3.getPosition().x,
                spacejunk3.getPosition().y,
                spacejunk3.getHitbox().width / 2,
                spacejunk3.getHitbox().height / 2,
                spacejunk3.getHitbox().width,
                spacejunk3.getHitbox().height,
                1, 1, rotation2, true
        );

        if (spacejunk3.getDestroyed()) {
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

        if (updater.getDevEnabled()) {

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
    private void drawSpacejunk4() {

        batch.draw(AssetLoader.junk4,
                spacejunk4.getPosition().x,
                spacejunk4.getPosition().y,
                spacejunk4.getHitbox().width,
                spacejunk4.getHitbox().height
        );

        if (spacejunk4.getDestroyed()) {
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

        if (updater.getDevEnabled()) {

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
    private void drawSpacejunk5() {

        batch.draw(AssetLoader.junk5,
                spacejunk5.getPosition().x,
                spacejunk5.getPosition().y,
                spacejunk5.getHitbox().width,
                spacejunk5.getHitbox().height
        );

        if (spacejunk5.getDestroyed()) {
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
            batch.setColor(1, 1, 1, 1);
        }

        //DEV
        if (updater.getDevEnabled()) {

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

    private void drawMoney() {


        //coin glow
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(AssetLoader.glowYellowSprite,
                updater.getCoin().getPosition().x - 43,
                updater.getCoin().getPosition().y - 45,
                64,
                64,
                128,
                128,
                1, 1, rotation, true
        );
        batch.setColor(1, 1, 1, 1);
        //coin 40 bgSpeed 40
        batch.draw(AssetLoader.coinSprite,
                updater.getCoin().getPosition().x,
                updater.getCoin().getPosition().y,
                updater.getCoin().getHitbox().width / 2,
                updater.getCoin().getHitbox().height / 2,
                updater.getCoin().getHitbox().width,
                updater.getCoin().getHitbox().height,
                1, 1, 90, true
        );


        //cashstack glow
        batch.draw(AssetLoader.glowGreenSprite,
                updater.getCashstack().getPosition().x - 80,
                updater.getCashstack().getPosition().y - 80,
                128,
                128,
                256,
                256,
                1, 1, rotation, true
        );

        //cashstack
        batch.draw(AssetLoader.cashstack,
                updater.getCashstack().getPosition().x,
                updater.getCashstack().getPosition().y,
                updater.getCashstack().getHitbox().height,
                updater.getCashstack().getHitbox().width);


        if (updater.getDevEnabled()) {

            shapeRenderer.rect(
                    updater.getCoin().getPosition().x,
                    updater.getCoin().getPosition().y,
                    updater.getCoin().getHitbox().width,
                    updater.getCoin().getHitbox().height);

            shapeRenderer.rect(
                    updater.getCashstack().getPosition().x,
                    updater.getCashstack().getPosition().y,
                    updater.getCashstack().getHitbox().width,
                    updater.getCashstack().getHitbox().height);
        }
    }

    private void drawCrates() {

        batch.draw(AssetLoader.woodenCrate, updater.getWoodenCrate().getPosition().x,
                updater.getWoodenCrate().getPosition().y,
                updater.getWoodenCrate().getHitbox().width,
                updater.getWoodenCrate().getHitbox().height);
    }

    private void drawWarning() {


        AssetLoader.font.setColor(1, 0.3f, 0.3f, 1);
        glyphLayout.setText(AssetLoader.font, "WARNING");
        glyphWidth = glyphLayout.width;
        glyphHeight = glyphLayout.height;
        AssetLoader.font.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2,
                (VIRTUAL_HEIGHT - glyphHeight) / 2);
        AssetLoader.font.setColor(0.141f, 0.848f, 0.407f, 1f);
    }

    public void drawHealth() {

        for (int i = 220; i < 860 - 1; i += 64) {

            if (updater.getSpacecraft().getHealth() < 3
                    && updater.getSpacecraft().getHealth() > 1) {
                batch.setColor(1f, 0.5f, 0.5f, 1);
            } else if (updater.getSpacecraft().getHealth() < 2) {
                batch.setColor(1, 0, 0, 1);
            } else {
                batch.setColor(1, 1, 1, 1);
            }

            batch.draw(AssetLoader.healthFrame, i, 64, 64, hudHeight);
        }

        for (int i = 0; i < 640 * (updater.getSpacecraft().getHealth() * 0.1) - 1; i += 64) {

            if (updater.getSpacecraft().getHealth() < 3
                    && updater.getSpacecraft().getHealth() > 1) {
                batch.setColor(1f, 0.5f, 0.5f, 1);
            } else if (updater.getSpacecraft().getHealth() < 2) {
                batch.setColor(1, 0, 0, 1);
            } else {
                batch.setColor(1, 1, 1, 1);
            }
            batch.draw(AssetLoader.health, i + 220, 64, 64, hudHeight);
        }

        batch.setColor(1, 1, 1, 1);

        glyphLayout.setText(AssetLoader.fontSmall, "HULL");
        glyphWidth = glyphLayout.width;
        //AssetLoader.fontSmall.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2, 160);
        AssetLoader.fontSmall.draw(batch, glyphLayout, 100, 88);

        //batch.draw(AssetLoader.armorUp3, 128, 128, 64, 64);
    }

    private void drawSpeed() {

        batch.setColor(1, 1, 1, 1);
        batch.draw(AssetLoader.speedFrameLeft, 220, speedFrameY, 64, hudHeight);

        for (int i = 286; i < 796 - 1; i += 64) {
            batch.draw(AssetLoader.speedFrameMiddle, i - 2, speedFrameY, 64, hudHeight);
        }

        batch.draw(AssetLoader.speedFrameRight, 796, speedFrameY, 64, hudHeight);


        for (int i = 0; i < updater.getSpeed() * 0.008; i++) {

            batch.draw(AssetLoader.speed, 226 + i * 8, speedFrameY, 64, hudHeight);
        }

        glyphLayout.setText(AssetLoader.fontSmall, "SPEED");
        glyphWidth = glyphLayout.width;
        //AssetLoader.fontSmall.draw(batch, glyphLayout, (VIRTUAL_WIDTH - glyphWidth) / 2, 270);
        AssetLoader.fontSmall.draw(batch, glyphLayout, 100, 128);
        //AssetLoader.fontSmall.setColor(0.141f, 0.848f, 0.407f, 1f);

    }

    private void drawAsteroid() {
        batch.draw(AssetLoader.asteroid, asteroidPosition.x, asteroidPosition.y);
    }

    private void drawPlanet() {
        batch.draw(AssetLoader.planet, planetPosition.x, planetPosition.y);
    }

    private void handleCalc() {

        planetPosition.y += -0.5;
        planetPosition.x += 0.1;
        asteroidPosition.y += -1;
        asteroidPosition.x -= 0.3;

        if (planetPosition.y < -300) {
            planetPosition.y = 2100;
            planetPosition.x = random.nextInt(1000);
        }
        if (asteroidPosition.y < -300) {
            asteroidPosition.y = 2100;
        }

        rotation2 -= 0.5;
        rotation += 0.5;
        if (rotation2 < 0f) rotation2 = 360f;
        if (rotation > 360f) rotation = 0f;

    }
}


package com.ikisoft.sstl.main;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ikisoft.sstl.gameobjects.Crate;
import com.ikisoft.sstl.gameobjects.Money;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;
import com.ikisoft.sstl.helpers.LerpHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Updater {

    //timer shit
    long startTime, nanoSeconds;
    long distance, speed;
    private GameState gameState;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private Money coin, cashstack;
    private Crate woodenCrate;
    private LerpHandler anchor, crateSplash;
    private boolean gameover, lowhealthPlayed, devEnabled, crateOpened,
            timerStarted;
    private int moneyThisRun;
    private Queue<Integer> crateQueue;
    private ArrayList<Integer> crateDrop;

    public enum GameState {

        START, MAINMENU, RUNNING, PAUSED, GAMEOVER,
        GAMEOVERSCREEN, OPTIONS, SHOP, INFO, CRATESPLASH, ITEMSPLASH;
    }

    public Updater() {

        gameState = GameState.START;

        spacecraft = new Spacecraft(284, 384, 128, 32);
        //cow
        spacejunk = new Spacejunk(200, 200, this);
        //fish
        spacejunk2 = new Spacejunk(150, 150, this);
        //can
        spacejunk3 = new Spacejunk(150, 150, this);
        //apple
        spacejunk4 = new Spacejunk(150, 150, this);
        //tire
        spacejunk5 = new Spacejunk(256, 256, this);
        coin = new Money(40, 40, this, 10, 2050);
        cashstack = new Money(100, 100, this, 50, 5000);
        //frequency 50k
        woodenCrate = new Crate(256, 256, this, 2500);
        anchor = new LerpHandler(200, 3000, 200, 1536);
        crateSplash = new LerpHandler(1000, 800, 300, 800);
        distance = 0;
        speed = 1;
        moneyThisRun = 0;
        lowhealthPlayed = false;
        crateQueue = new LinkedList<Integer>();
        crateDrop = new ArrayList<Integer>();
        crateOpened = false;

        startTime = 0;
        nanoSeconds = 0;

    }

    public void update(float delta) {

        if (speed <= 0) speed = 1;
        if (speed > 10000) speed = 10000;


        switch (gameState) {
            case START:
                anchor.getPosition().lerp(anchor.getTarget(), 0.1f);
                updateStart();
                break;
            case MAINMENU:
                updateMainmenu();
                break;
            case RUNNING:
                timerStarted = false;
                crateSplash.reset(2000, 800);
                anchor.reset(200, 3000);
                updateRunning(delta);
                break;
            case PAUSED:
                break;
            case GAMEOVER:

                //TODO change to system current time nanoseconds (works?)
                //make a class maybe?
                if (waitFor(5)) {
                    if (!crateQueue.isEmpty()) {
                        gameState = GameState.CRATESPLASH;
                    } else {
                        gameState = GameState.GAMEOVERSCREEN;
                    }
                }
                AssetLoader.lowhealth.stop();
                if (!gameover) {
                    save();
                    //AssetLoader.explosion.play();
                }

                gameover = true;

                updateGameover(delta);


                break;
            case GAMEOVERSCREEN:
                anchor.getPosition().lerp(anchor.getTarget(), 0.1f);

                break;
            case OPTIONS:
                updateOptions();
                break;
            case INFO:
                updateInfo();
                break;
            case SHOP:
                break;
            case CRATESPLASH:

                anchor.getPosition().lerp(anchor.getTarget(), 0.1f);
                updateCratesplash();

                break;
            case ITEMSPLASH:

            default:
                break;
        }
    }

    private boolean waitFor(long x) {

        if (!timerStarted) {
            nanoSeconds = x * 1000000000L;
            startTime = TimeUtils.nanoTime();
            timerStarted = true;
        }
        if (TimeUtils.timeSinceNanos(startTime) > nanoSeconds) {
            return true;
        }
        return false;
    }

    public void resetGame() {
        spacecraft.reset();
        spacejunk.reset();
        spacejunk2.reset();
        spacejunk3.reset();
        spacejunk4.reset();
        spacejunk5.reset();
        gameState = GameState.RUNNING;
        distance = 0;
        speed = 0;
        startTime = 0;
        lowhealthPlayed = false;
        gameover = false;
        moneyThisRun = 0;
        crateQueue.clear();
        crateOpened = false;

        for(Integer i : crateQueue){
            crateQueue.poll();

        }

    }

    private void updateInfo() {
    }

    private void updateOptions() {
    }

    private void updateStart() {


    }

    private void updateMainmenu() {
    }

    private void updateRunning(float delta) {

        distance += 1;
        speed += 1;

        if (speed > 1000) {
            cashstack.update(delta);
        } else if (speed < 1000) {
            cashstack.reset();
        }


        coin.update(delta);
        woodenCrate.update(delta);
        spacecraft.update(delta);
        spacejunk.update(delta);
        spacejunk2.update(delta);
        spacejunk3.update(delta);
        spacejunk4.update(delta);
        spacejunk5.update(delta);
        updateCollision();


        //Play warning sound
        if (spacecraft.getHealth() < 2 && !lowhealthPlayed) {
            AssetLoader.lowhealth.play();
            lowhealthPlayed = true;

        }
    }

    private void updatePaused() {

    }

    private void updateGameover(float delta) {


        speed -= 10;
        if (speed <= 0) speed = 1;

        spacecraft.update(delta);
        spacejunk.update(delta);
        spacejunk2.update(delta);
        spacejunk3.update(delta);
        spacejunk4.update(delta);
        spacejunk5.update(delta);
        coin.update(delta);
        cashstack.update(delta);
        //resetGame();
    }

    private void updateCratesplash() {

        crateSplash.getPosition().lerp(crateSplash.getTarget(), 0.1f);
    }

    private void updateCollision() {

        //Cow
        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk.getHitbox())) {


            if (spacecraft.getHealth() == 0) {
                spacejunk.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk.getDestroyed()) {

                spacejunk.setDestroyed();
                spacecraft.setHealth();
                speed -= 500;

                if (random.nextInt(1) == 0) {
                    spacecraft.setVelocityLeft(3);
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight(3);
                }

                AssetLoader.spacecraftHit.play();
                AssetLoader.moo.play();

            }


            //fish
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk2.getHitbox())) {

            if (spacecraft.getHealth() == 0) {
                spacejunk2.setDestroyed();
                gameState = GameState.GAMEOVER;
            }

            if (!spacejunk2.getDestroyed()) {
                spacejunk2.setDestroyed();
                spacecraft.setHealth();
                speed -= 100;

                AssetLoader.spacecraftHit3.play();

            }


            //can
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk3.getHitbox())) {

            if (spacecraft.getHealth() == 0) {
                spacejunk3.setDestroyed();
                gameState = GameState.GAMEOVER;
            }

            if (!spacejunk3.getDestroyed()) {
                spacejunk3.setDestroyed();
                spacecraft.setHealth();
                speed -= 150;

                AssetLoader.spacecraftHit5.play();

            }


            //apple
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk4.getHitbox())) {

            if (spacecraft.getHealth() == 0) {
                spacejunk4.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk4.getDestroyed()) {
                spacejunk4.setDestroyed();
                spacecraft.setHealth();
                speed -= 100;

                AssetLoader.spacecraftHit4.play();

            }

            //Tire
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk5.getHitbox())) {

            if (spacecraft.getHealth() == 0) {
                spacejunk5.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk5.getDestroyed()) {
                spacejunk5.setDestroyed();
                spacecraft.setHealth();
                speed -= 350;

                if (random.nextInt(1) == 0) {
                    spacecraft.setVelocityLeft(3);
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight(3);
                }

                AssetLoader.spacecraftHit2.play();

            }


        }

        if (Intersector.overlaps(spacecraft.getHitbox(), coin.getHitbox())) {

            if (!coin.getCollected()) {
                coin.setCollected();
                AssetLoader.cashSound.play();
                moneyThisRun += 10;
            }
        }


        if (Intersector.overlaps(spacecraft.getHitbox(), cashstack.getHitbox())) {

            if (!cashstack.getCollected()) {
                cashstack.setCollected();
                AssetLoader.cashSound.play();
                moneyThisRun += 50;
            }
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), woodenCrate.getHitbox())) {

            if (!woodenCrate.getCollected()) {
                woodenCrate.setCollected();
                AssetLoader.cashSound.play();
                crateQueue.add(1);
            }
        }
    }

    public void openCrate() {


        if (crateQueue.poll() == 1) {
            generateLoot();
            gameState = GameState.ITEMSPLASH;
        }
    }

    private void generateLoot() {

        for (int i = 0; i < 3; i++) {
            crateDrop.add(i, random.nextInt(100));
        }

        for (int i = 0; i < 3; i++) {
            if (crateDrop.get(i) >= 0 && crateDrop.get(i) <= 30) {
                crateDrop.add(i, 1);

            } else if (crateDrop.get(i) > 30 && crateDrop.get(i) <= 60) {
                crateDrop.add(i, 2);

            } else if (crateDrop.get(i) > 60 && crateDrop.get(i) <= 90) {
                crateDrop.add(i, 3);

            } else if (crateDrop.get(i) > 90 && crateDrop.get(i) <= 98) {
                crateDrop.add(i, 4);

            } else if (crateDrop.get(i) > 98 && crateDrop.get(i) <= 100) {
                crateDrop.add(i, 5);
            }
        }
    }

    private void save() {
        DataHandler.money += moneyThisRun;
        DataHandler.save();
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Spacejunk getSpacejunk() {
        return spacejunk;
    }

    public Spacejunk getSpacejunk2() {
        return spacejunk2;
    }

    public Spacejunk getSpacejunk3() {
        return spacejunk3;
    }

    public Spacejunk getSpacejunk4() {
        return spacejunk4;
    }

    public Spacejunk getSpacejunk5() {
        return spacejunk5;
    }

    public Money getCoin() {
        return coin;
    }

    public Money getCashstack() {
        return cashstack;
    }

    public Crate getWoodenCrate() {
        return woodenCrate;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Vector2 getAnchorPos() {
        return anchor.getPosition();
    }

    public LerpHandler getAnchor() {
        return anchor;
    }

    public Vector2 getCrateLerp() {
        return crateSplash.getPosition();
    }

    public long getDistance() {
        return distance;

    }

    public long getSpeed() {
        return speed;

    }

    public boolean getDevEnabled() {
        return devEnabled;
    }

    public void setDevEnabled() {
        if (devEnabled) {
            devEnabled = false;
        } else {
            devEnabled = true;
        }
    }

    public int getMoneyThisRun() {
        return moneyThisRun;
    }

    public Queue<Integer> getCrateQueue() {
        return crateQueue;
    }

    public ArrayList<Integer> getCrateDrop() {
        return crateDrop;
    }

    public boolean getCrateOpened() {
        return crateOpened;
    }


}

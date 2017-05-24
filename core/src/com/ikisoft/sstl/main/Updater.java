package com.ikisoft.sstl.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.ikisoft.sstl.gameobjects.Crate;
import com.ikisoft.sstl.gameobjects.Money;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;
import com.ikisoft.sstl.helpers.LerpHandler;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Updater {

    long startTime;
    long distance, speed;
    private GameState gameState;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private Money coin, cashstack;
    private Crate woodenCrate;
    private LerpHandler logo;
    private boolean dataSaved, gameover, lowhealthPlayed, devEnabled;
    private int moneyThisRun;

    public enum GameState{

        START, MAINMENU, RUNNING, PAUSED, GAMEOVER, OPTIONS, SHOP, INFO,

    }

    public Updater (){

        gameState = GameState.START;

        spacecraft = new Spacecraft(540, 384, 128, 128);
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
        //start y  2300
        coin = new Money(40, 40, this, 10, 2050);
        cashstack = new Money(100, 100, this, 50, 5000);
        woodenCrate = new Crate(256, 256, this, 50000);
        logo = new LerpHandler(200, 2600, 200, 1536);
        distance = 0;
        speed = 1;
        moneyThisRun = 0;

        startTime = TimeUtils.nanoTime();
        dataSaved = false;
        lowhealthPlayed = false;


    }

    public void update(float delta){

        if(speed <= 0)speed = 1;
        if(speed > 10000)speed = 10000;

        switch (gameState){
            case START:
                updateStart();
                break;
            case MAINMENU:
                updateMainmenu();
                break;
            case RUNNING:
                dataSaved = false;
                updateRunning(delta);
                break;
            case PAUSED:
                break;
            case GAMEOVER:

                AssetLoader.lowhealth.stop();
                if(!gameover){
                    save();
                    //AssetLoader.explosion.play();
                }

                gameover = true;

                updateGameover(delta);
                break;
            case OPTIONS:
                updateOptions();
                break;
            case INFO:
                updateInfo();
                break;
            case SHOP:
                break;
            default:
                break;
        }
    }

    private void updateInfo() {
    }

    private void updateOptions() {
    }

    private void updateStart() {

        logo.getPosition().lerp(logo.getTarget(), 0.1f);

    }

    private void updateMainmenu() {
    }

    private void updateRunning(float delta) {

        distance += 1;
        speed += 1;

        if(speed > 1000){
            cashstack.update(delta);
        } else if (speed < 1000){
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
        if(spacecraft.getHealth() < 2 && !lowhealthPlayed){
            AssetLoader.lowhealth.play();
            lowhealthPlayed = true;

        }
    }

    private void updatePaused(){

    }

    private void updateGameover(float delta){


        speed -= 10;
        if(speed <= 0)speed = 1;

        spacejunk.update(delta);
        spacejunk2.update(delta);
        spacejunk3.update(delta);
        spacejunk4.update(delta);
        spacejunk5.update(delta);
        coin.update(delta);
        cashstack.update(delta);
        //resetGame();
    }

    public void resetGame(){
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
    }

    private void updateCollision() {

        //Cow
        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk.getHitbox())) {



            if (!spacejunk.getDestroyed()) {

                spacejunk.setDestroyed();
                spacecraft.setHealth();
                speed -= 500;

                AssetLoader.spacecraftHit.play();
                AssetLoader.moo.play();

            } else if (spacecraft.getHealth() == 0){
                spacejunk.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            //fish
        }

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk2.getHitbox())) {

            if (!spacejunk2.getDestroyed()) {
                spacejunk2.setDestroyed();
                spacecraft.setHealth();
                speed -= 100;

                AssetLoader.spacecraftHit3.play();

            } else if (spacecraft.getHealth() == 0){
                spacejunk2.setDestroyed();

                    gameState = GameState.GAMEOVER;


            }

            //can
        }

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk3.getHitbox())) {

            if (!spacejunk3.getDestroyed()) {
                spacejunk3.setDestroyed();
                spacecraft.setHealth();
                speed -= 150;

                AssetLoader.spacecraftHit5.play();

            } else if (spacecraft.getHealth() == 0){
                spacejunk3.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            //apple
        }

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk4.getHitbox())) {


            if (!spacejunk4.getDestroyed()) {
                spacejunk4.setDestroyed();
                spacecraft.setHealth();
                speed -= 100;

                AssetLoader.spacecraftHit4.play();

            } else if (spacecraft.getHealth() == 0){
                spacejunk4.setDestroyed();
                gameState = GameState.GAMEOVER;

            }
        //Tire
        }

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk5.getHitbox())) {

            if (!spacejunk5.getDestroyed()) {
                spacejunk5.setDestroyed();
                spacecraft.setHealth();
                speed -= 350;

                AssetLoader.spacecraftHit2.play();

            } else if (spacecraft.getHealth() == 0){
                spacejunk5.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

        }

        if(Intersector.overlaps(spacecraft.getHitbox(), coin.getHitbox())) {

            if (!coin.getCollected()) {
                coin.setCollected();
                AssetLoader.cashSound.play();
                moneyThisRun += 10;
            }
        }


        if(Intersector.overlaps(spacecraft.getHitbox(), cashstack.getHitbox())) {

            if (!cashstack.getCollected()) {
                cashstack.setCollected();
                AssetLoader.cashSound.play();
                moneyThisRun += 50;
            }
        }

        if(Intersector.overlaps(spacecraft.getHitbox(), woodenCrate.getHitbox())) {

            if (!woodenCrate.getCollected()) {
                woodenCrate.setCollected();
                AssetLoader.cashSound.play();
            }
        }
    }

    private void save(){
        DataHandler.money += moneyThisRun;
        DataHandler.save();
    }


    public Spacecraft getSpacecraft(){
        return spacecraft;
    }

    public Spacejunk getSpacejunk(){
        return spacejunk;
    }

    public Spacejunk getSpacejunk2(){
        return spacejunk2;
    }

    public Spacejunk getSpacejunk3(){
        return spacejunk3;
    }

    public Spacejunk getSpacejunk4(){
        return spacejunk4;
    }

    public Spacejunk getSpacejunk5(){
        return spacejunk5;
    }

    public Money getCoin(){
        return coin;
    }

    public Money getCashstack(){
        return cashstack;
    }

    public Crate getWoodenCrate(){
        return woodenCrate;
    }

    public GameState getGameState(){
        return gameState;
    }

    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    public Vector2 getLogo(){
        return logo.getPosition();
    }

    public LerpHandler getLogoLerp(){
        return logo;
    }

    public long getDistance(){
        return distance;

    }

    public long getSpeed(){
        return speed;

    }

    public boolean getDevEnabled(){
        return devEnabled;
    }

    public void setDevEnabled(){
        if(devEnabled){
            devEnabled = false;
        } else {
            devEnabled = true;
        }
    }

    public int getMoneyThisRun(){
        return moneyThisRun;
    }


}

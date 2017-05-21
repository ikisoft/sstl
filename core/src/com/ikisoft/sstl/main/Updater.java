package com.ikisoft.sstl.main;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.LerpHandler;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Updater {

    private GameState gameState;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private LerpHandler logo;
    private float distance;

    public enum GameState{

        START, MAINMENU, RUNNING, PAUSED, GAMEOVER, OPTIONS, SHOP, INFO,

    }

    public Updater (){

        gameState = GameState.START;

        spacecraft = new Spacecraft(284, 384, 128, 200);
        //cow
        spacejunk = new Spacejunk(300, 300, this);
        //fish
        spacejunk2 = new Spacejunk(200, 200, this);
        //can
        spacejunk3 = new Spacejunk(150, 150, this);
        //apple
        spacejunk4 = new Spacejunk(150, 150, this);
        spacejunk5 = new Spacejunk(256, 256, this);

        logo = new LerpHandler(200, 2300, 200, 1536);
        distance = 0;

    }

    public void update(float delta){

        switch (gameState){
            case START:
                updateStart();
                break;
            case MAINMENU:
                updateMainmenu();
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSED:
                break;
            case GAMEOVER:
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

        distance += 0.1;

        spacecraft.update(delta);
        spacejunk.update(delta);
        spacejunk2.update(delta);
        spacejunk3.update(delta);
        spacejunk4.update(delta);
        spacejunk5.update(delta);
        updateCollision();



    }

    private void updatePaused(){

    }

    private void updateGameover(float delta){
        spacejunk.update(delta);
        spacejunk2.update(delta);
        spacejunk3.update(delta);
        spacejunk4.update(delta);
        spacejunk5.update(delta);
        updateCollision();
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
    }

    private void updateCollision() {

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk.getHitbox())) {

            if (!spacejunk.getDestroyed()) {
                spacejunk.setDestroyed();
                spacecraft.setHealth();

            } else if (spacecraft.getHealth() == 0){
                spacejunk.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

        } if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk2.getHitbox())) {

            if (!spacejunk2.getDestroyed()) {
                spacejunk2.setDestroyed();
                spacecraft.setHealth();

            } else if (spacecraft.getHealth() == 0){
                spacejunk2.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

        } if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk3.getHitbox())) {

            if (!spacejunk3.getDestroyed()) {
                spacejunk3.setDestroyed();
                spacecraft.setHealth();

            } else if (spacecraft.getHealth() == 0){
                spacejunk3.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

        } if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk4.getHitbox())) {

            if (!spacejunk4.getDestroyed()) {
                spacejunk4.setDestroyed();
                spacecraft.setHealth();

            } else if (spacecraft.getHealth() == 0){
                spacejunk4.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

        } if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk5.getHitbox())) {

            if (!spacejunk5.getDestroyed()) {
                spacejunk5.setDestroyed();
                spacecraft.setHealth();

            } else if (spacecraft.getHealth() == 0){
                spacejunk5.setDestroyed();
                gameState = GameState.GAMEOVER;

            }
        }
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

    public float getDistance(){
        return distance;

    }


}

package com.ikisoft.sstl.main;

import com.badlogic.gdx.math.Intersector;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Updater {

    private GameState gameState;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk;


    public enum GameState{

        START, MAINMENU, RUNNING, PAUSED, GAMEOVER, OPTIONS, SHOP,

    }

    public Updater (){

        gameState = GameState.RUNNING;
        spacecraft = new Spacecraft(284, 256, 256, 256);
        spacejunk = new Spacejunk(240, 1600, 100, 100);

    }

    public void update(float delta){

        switch (gameState){
            case START:
                break;
            case MAINMENU:
                break;
            case RUNNING:
                updateRuninng(delta);
                break;
            case PAUSED:
                break;
            case GAMEOVER:
                updateGameover();
                break;
            case OPTIONS:
                break;
            case SHOP:
                break;
            default:
                break;
        }
    }

    private void updateRuninng(float delta) {

        spacecraft.update(delta);
        spacejunk.update(delta);
        updateCollision();
    }

    private void updatePaused(){

    }

    private void updateGameover(){
        resetGame();
    }

    private void resetGame(){
        spacecraft.reset();
        spacejunk.reset();
        gameState = GameState.RUNNING;
    }

    private void updateCollision() {

        if(Intersector.overlaps(spacecraft.getHitbox(), spacejunk.getHitbox())){
            gameState = GameState.GAMEOVER;

        }
    }


    public Spacecraft getSpacecraft(){
        return spacecraft;
    }

    public Spacejunk getSpacejunk(){
        return spacejunk;
    }

    public GameState getGameState(){
        return gameState;
    }




}

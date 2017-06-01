package com.ikisoft.sstl.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.ikisoft.sstl.gameobjects.Crate;
import com.ikisoft.sstl.gameobjects.Money;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;
import com.ikisoft.sstl.helpers.LerpHandler;
import com.ikisoft.sstl.helpers.Timer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Updater {

    //timer shit
    private long startTime, nanoSeconds;
    private long distance, speed;
    private long id;
    private boolean soundPlayed;
    private Timer timer;
    private GameState gameState;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk, spacejunk2, spacejunk3, spacejunk4, spacejunk5;
    private Money coin, cashstack;
    private Crate woodenCrate;
    private Crate steelCrate;
    private LerpHandler anchor, crateSplash;
    private boolean gameover, lowhealthPlayed, devEnabled, crateOpened,
            timerStarted;
    private int moneyThisRun, lootNumber, randomMeme;
    private Queue<Integer> crateQueue;
    private ArrayList<Integer> crateDrop;

    private Interpolation easAlpha = Interpolation.swingIn;
    private int lifeTime = 2;
    private float foleyVolume = 0;
    private float elapsed = 0;
    private float itemAlpha = 0;
    private float progress = 0;

    public enum GameState {

        START, MAINMENU, RUNNING, PAUSED, GAMEOVER,
        GAMEOVERSCREEN, OPTIONS, SHIP, ITEMS, SYSTEMS, REPAIR, STATS, INFO,
        CRATESPLASH, CRATECHOOSE, ITEMSPLASH, DESTROYED;
    }

    public Updater() {

        timer = new Timer();
        gameState = GameState.START;
        soundPlayed = false;

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
        steelCrate = new Crate(256, 256, this, 2500);
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
        lootNumber = 0;
        randomMeme = 0;

    }

    public void update(float delta) {
        spacecraft.setHit(false);

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
                AssetLoader.theme.stop();


                foleyVolume += Gdx.graphics.getDeltaTime() / 25;
                if (foleyVolume > 0.5) foleyVolume = 0.5f;
                //again this has to be done because of android loop gap...
                if (!soundPlayed) {
                    id = AssetLoader.spaceFoley.play();
                    AssetLoader.spaceFoley.setLooping(id, true);
                    soundPlayed = true;
                    AssetLoader.spaceFoley.setVolume(id, foleyVolume);
                }

                timerStarted = false;
                crateSplash.reset(2000, 800);
                anchor.reset(200, 3000);
                updateRunning(delta);
                break;
            case PAUSED:
                break;
            case GAMEOVER:

                randomMeme = random.nextInt(8);
                updateGameover(delta);
                AssetLoader.lowhealth.stop();
                AssetLoader.spaceFoley.stop();
                soundPlayed = false;

                //AssetLoader.explosion.play();
                if (!gameover) {


                    DataHandler.vehicleCondition -= 3;
                    AssetLoader.explosion.play();
                    save();
                    gameover = true;

                }

                if (timer.waitFor(5)) {

                    if(DataHandler.vehicleCondition <= 0){
                        gameState = GameState.DESTROYED;
                    } else if (!crateQueue.isEmpty()) {
                        gameState = GameState.CRATESPLASH;
                    } else {
                        gameState = GameState.GAMEOVERSCREEN;
                    }
                }


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
            case SHIP:
                break;
            case SYSTEMS:
                break;
            case ITEMS:
                break;
            case REPAIR:
                break;
            case STATS:
                break;
            case CRATESPLASH:
                progress = 0;
                lifeTime = 2;
                elapsed = 0;
                itemAlpha = 0;
                anchor.getPosition().lerp(anchor.getTarget(), 0.1f);
                updateCratesplash();
                break;
            case ITEMSPLASH:
                elapsed += Gdx.graphics.getDeltaTime();
                progress = Math.min(1f, elapsed / lifeTime);
                itemAlpha = easAlpha.apply(progress);
                break;
            case DESTROYED:
                break;
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
        timer.resetTime();
        lowhealthPlayed = false;
        gameover = false;
        moneyThisRun = 0;
        crateQueue.clear();
        crateOpened = false;

        for (Integer i : crateQueue) {
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
        steelCrate.update(delta);
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
        woodenCrate.update(delta);
        //resetGame();
    }

    private void updateCratesplash() {

        crateSplash.getPosition().lerp(crateSplash.getTarget(), 0.1f);
    }

    private void updateCollision() {


        //Cow
        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk.getHitbox())) {
            spacecraft.setHit(true);

            if (spacecraft.getHealth() == 0) {
                spacejunk.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk.getDestroyed()) {

                spacejunk.setDestroyed();
                speed -= 500 / DataHandler.kineticBarrierLevel;

                System.err.println(spacecraft.getEnergyDestroyed());
                if (spacecraft.getEnergyDestroyed()) {
                    spacecraft.setHealth();
                }
                spacecraft.setEnergy(100);

                if (random.nextInt(2) == 0) {
                    spacecraft.setVelocityLeft((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                }

                AssetLoader.spacecraftHit.play();
                AssetLoader.moo.play();

            }


            //fish
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk2.getHitbox())) {
            spacecraft.setHit(true);

            if (spacecraft.getHealth() == 0) {
                spacejunk2.setDestroyed();
                gameState = GameState.GAMEOVER;
            }

            if (!spacejunk2.getDestroyed()) {
                spacejunk2.setDestroyed();

                if (spacecraft.getEnergyDestroyed()) {
                    spacecraft.setHealth();
                }

                spacecraft.setEnergy(100);
                speed -= 100 / DataHandler.kineticBarrierLevel;

                if (random.nextInt(2) == 0) {
                    spacecraft.setVelocityLeft((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                }

                AssetLoader.spacecraftHit3.play();

            }


            //can
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk3.getHitbox())) {
            spacecraft.setHit(true);
            if (spacecraft.getHealth() == 0) {
                spacejunk3.setDestroyed();
                gameState = GameState.GAMEOVER;
            }

            if (!spacejunk3.getDestroyed()) {
                spacejunk3.setDestroyed();

                if (spacecraft.getEnergyDestroyed()) {
                    spacecraft.setHealth();
                }
                spacecraft.setEnergy(100);

                speed -= 150 / DataHandler.kineticBarrierLevel;

                if (random.nextInt(2) == 0) {
                    spacecraft.setVelocityLeft((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                }

                AssetLoader.spacecraftHit5.play();

            }

            //apple
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk4.getHitbox())) {
            spacecraft.setHit(true);
            if (spacecraft.getHealth() == 0) {
                spacejunk4.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk4.getDestroyed()) {
                spacejunk4.setDestroyed();

                if (spacecraft.getEnergyDestroyed()) {
                    spacecraft.setHealth();
                }
                spacecraft.setEnergy(100);
                speed -= 100 / DataHandler.kineticBarrierLevel;

                if (random.nextInt(2) == 0) {
                    spacecraft.setVelocityLeft((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                }

                AssetLoader.spacecraftHit4.play();

            }

            //Tire
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), spacejunk5.getHitbox())) {
            spacecraft.setHit(true);
            if (spacecraft.getHealth() == 0) {
                spacejunk5.setDestroyed();
                gameState = GameState.GAMEOVER;

            }

            if (!spacejunk5.getDestroyed()) {
                spacejunk5.setDestroyed();

                if (spacecraft.getEnergyDestroyed()) {
                    spacecraft.setHealth();
                }
                spacecraft.setEnergy(100);
                speed -= 500 / DataHandler.kineticBarrierLevel;

                if (random.nextInt(2) == 0) {
                    spacecraft.setVelocityLeft((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
                    spacecraft.setVelocityRight(0);
                } else {
                    spacecraft.setVelocityLeft(0);
                    spacecraft.setVelocityRight((float) (5 / Math.sqrt(DataHandler.kineticBarrierLevel)));
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
                moneyThisRun += 100;
            }
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), woodenCrate.getHitbox())) {

            if (!woodenCrate.getCollected()) {
                woodenCrate.setCollected();
                AssetLoader.grab.play();
                crateQueue.add(1);
            }
        }

        if (Intersector.overlaps(spacecraft.getHitbox(), steelCrate.getHitbox())) {

            if (!steelCrate.getCollected()) {
                steelCrate.setCollected();
                AssetLoader.grab.play();
                crateQueue.add(2);
            }
        }
    }

    public void openCrate() {

        if (crateQueue.peek() == 1 && DataHandler.money >= 1000) {
            crateQueue.poll();
            AssetLoader.woodenCrateBreak.play();
            generateLoot(1);
            gameState = GameState.ITEMSPLASH;
            DataHandler.money -= 1000;
        } else if (crateQueue.peek() == 2 && DataHandler.money >= 2500) {
            crateQueue.poll();
            AssetLoader.woodenCrateBreak.play();
            generateLoot(2);
            gameState = GameState.ITEMSPLASH;
            DataHandler.money -= 2500;
        } else {
            System.out.println("NO MONEY NIGGA");
            crateQueue.poll();
            if(!crateQueue.isEmpty()){
                crateSplash.reset(2000, 800);
                gameState = GameState.CRATESPLASH;
            } else {
                gameState = GameState.GAMEOVERSCREEN;
            }
        }
    }

    public void nextCrate(){
        crateQueue.poll();
        if(!crateQueue.isEmpty()){
            crateSplash.reset(2000, 800);
            gameState = GameState.CRATESPLASH;
        } else {
            gameState = GameState.GAMEOVERSCREEN;
        }
    }

    //Forgive me, old gods of clean code... :(
    private void generateLoot(int crateType) {

        //Wooden crate loot
        if(crateType == 1){

            for (int i = 0; i < 3; i++) {
                lootNumber = random.nextInt(100);

                if (lootNumber >= 97 && lootNumber <= 99) {
                    crateDrop.add(i, random.nextInt(8 - 5) + 5);
                }else if (lootNumber >= 93 && lootNumber < 97) {
                    crateDrop.add(i, 4);
                } else if (lootNumber >= 50 && lootNumber < 93) {
                    crateDrop.add(i, 3);
                } else {
                    crateDrop.add(i, random.nextInt(3));
                }
            }

            for (int i = 0; i < 3; i++) {
                if (crateDrop.get(i) == 8) {
                    DataHandler.epicArmor++;
                } else if (crateDrop.get(i) == 7) {
                    DataHandler.epicThruster++;
                } else if (crateDrop.get(i) == 6) {
                    DataHandler.epicKinetic++;
                } else if (crateDrop.get(i) == 5) {
                    DataHandler.epicEnergy++;
                } else if (crateDrop.get(i) == 4) {
                    DataHandler.core++;
                } else if (crateDrop.get(i) == 3) {
                    DataHandler.rod++;
                } else if (crateDrop.get(i) == 2) {
                    DataHandler.scrap++;
                } else if (crateDrop.get(i) == 1) {
                    DataHandler.money += 200;
                } else if (crateDrop.get(i) == 0) {
                    DataHandler.money += 100;
                }
            }
            //Steel crate loot
        } else if(crateType == 2){

            for (int i = 0; i < 3; i++) {
                lootNumber = random.nextInt(100);

                if (lootNumber >= 94 && lootNumber <= 99) {
                    crateDrop.add(i, 8);
                }else if (lootNumber >= 89 && lootNumber < 94) {
                    crateDrop.add(i, 7);
                } else if (lootNumber >= 84 && lootNumber < 89) {
                    crateDrop.add(i, 6);
                } else if (lootNumber >= 74 && lootNumber < 79) {
                    crateDrop.add(i, 5);
                } else {
                    crateDrop.add(i, random.nextInt(5));
                }
            }

            for (int i = 0; i < 3; i++) {
                if (crateDrop.get(i) == 8) {
                    DataHandler.epicEnergy++;
                } else if (crateDrop.get(i) == 7) {
                    DataHandler.epicKinetic++;
                } else if (crateDrop.get(i) == 6) {
                    DataHandler.epicThruster++;
                } else if (crateDrop.get(i) == 5) {
                    DataHandler.epicArmor++;
                } else if (crateDrop.get(i) == 4) {
                    DataHandler.core++;
                } else if (crateDrop.get(i) == 3) {
                    DataHandler.rod++;
                } else if (crateDrop.get(i) == 2) {
                    DataHandler.scrap++;
                } else if (crateDrop.get(i) == 1) {
                    DataHandler.money += 200;
                } else if (crateDrop.get(i) == 0) {
                    DataHandler.money += 100;
                }
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

    public Crate getSteelCrate() {
        return steelCrate;
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

    public Queue<Integer> getCrateQueue() throws NullPointerException {
        return crateQueue;
    }

    public ArrayList<Integer> getCrateDrop() {
        return crateDrop;
    }

    public boolean getCrateOpened() {
        return crateOpened;
    }

    public float getItemAlpha() {
        return itemAlpha;
    }

    public int getRandomMeme() {
        return randomMeme;
    }


}

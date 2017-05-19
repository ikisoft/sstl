package com.ikisoft.sstl.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ikisoft.sstl.gameobjects.Spacecraft;
import com.ikisoft.sstl.gameobjects.Spacejunk;

/**
 * Created by maxikahe on 19.5.2017.
 */

public class Renderer {

    private Updater updater;
    private Spacecraft spacecraft;
    private Spacejunk spacejunk;
    private static final float VIRTUAL_WIDTH = 1080;
    private static final float VIRTUAL_HEIGHT = 1920;
    private static OrthographicCamera camera;
    private float runTime;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public Renderer(Updater updater){

        this.updater = updater;
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

    }

    public void render(){
        spacecraft = updater.getSpacecraft();
        spacejunk = updater.getSpacejunk();
        runTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(
                spacecraft.getPosition().x,
                spacecraft.getPosition().y,
                spacecraft.getHitbox().width,
                spacecraft.getHitbox().height);
        shapeRenderer.rect(
                spacejunk.getPosition().x,
                spacejunk.getPosition().y,
                spacejunk.getHitbox().width,
                spacejunk.getHitbox().height);
        shapeRenderer.end();
    }
}

package com.ikisoft.sstl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.ikisoft.sstl.helpers.AssetLoader;
import com.ikisoft.sstl.helpers.DataHandler;
import com.ikisoft.sstl.helpers.GestureHandler;
import com.ikisoft.sstl.helpers.InputHandler;
import com.ikisoft.sstl.helpers.StringConstants;
import com.ikisoft.sstl.main.Renderer;
import com.ikisoft.sstl.main.Updater;

public class SSTL extends ApplicationAdapter {

	private float delta, runTime;
	private int screenWidth, screenHeight;
	private Updater updater;
	private Renderer renderer;
	private InputMultiplexer multiplexer;



	@Override
	public void create () {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		DataHandler.load();
		AssetLoader.load();
		updater = new Updater();
		renderer = new Renderer(updater);
		StringConstants.loadStrings(updater);


		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new InputHandler(updater,
				screenWidth, screenHeight));
		multiplexer.addProcessor(new GestureDetector(new GestureHandler(
				updater,
				updater.getSpacecraft(),
				screenWidth, screenHeight)));
		Gdx.input.setInputProcessor(multiplexer);

		runTime = 0;

	}

	@Override
	public void render () {
		delta = Gdx.graphics.getDeltaTime() * 60;
		runTime += Gdx.graphics.getDeltaTime();
		if(delta > 1)delta = 1;
		if(delta < 0.033)delta = 0.033f;
		updater.update(delta);
		renderer.render(runTime);

	}
	
	@Override
	public void dispose () {
		AssetLoader.dispose();
		DataHandler.save();

	}
}

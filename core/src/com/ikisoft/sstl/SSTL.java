package com.ikisoft.sstl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.ikisoft.sstl.helpers.GestureHandler;
import com.ikisoft.sstl.helpers.InputHandler;
import com.ikisoft.sstl.main.Renderer;
import com.ikisoft.sstl.main.Updater;

public class SSTL extends ApplicationAdapter {

	private float delta;
	private int screenWidth, screenHeight;
	private Updater updater;
	private Renderer renderer;
	private InputMultiplexer multiplexer;


	@Override
	public void create () {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		updater = new Updater();
		renderer = new Renderer(updater);
		multiplexer = new InputMultiplexer();

		multiplexer.addProcessor(new GestureDetector(new GestureHandler(
				updater,
				updater.getSpacecraft(),
				screenWidth, screenHeight)));

		multiplexer.addProcessor(new InputHandler(updater,
				screenWidth, screenHeight));
		Gdx.input.setInputProcessor(multiplexer);

	}

	@Override
	public void render () {
		delta = Gdx.graphics.getDeltaTime() * 60;
		if(delta > 1)delta = 1;
		if(delta < 0.033)delta = 0.033f;
		updater.update(delta);
		renderer.render();

	}
	
	@Override
	public void dispose () {

	}
}

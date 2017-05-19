package com.ikisoft.sstl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ikisoft.sstl.SSTL;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 270;
		config.height = 480;
		//config.foregroundFPS = 5;

		new LwjglApplication(new SSTL(), config);

	}
}

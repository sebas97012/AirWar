package com.itcr.ce.airwar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itcr.ce.airwar.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int width = 1280;
		int height = 720 ;

		config.title = "AirWar 1: The Beginning";
		config.width = width;
		config.height = height;

		new LwjglApplication(new MyGdxGame(width, height), config);

	}
}

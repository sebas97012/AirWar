package com.itcr.ce.airwar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.itcr.ce.airwar.MyGdxGame;
import com.itcr.ce.airwar.server.Bridge;

public class DesktopLauncher {
	static Bridge bridge;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int width = 1280;
		int height = 720 ;

		config.title = "AirWar 1: The Beginning";
		config.width = width;
		config.height = height;
		bridge = new Bridge();
		new LwjglApplication(new MyGdxGame(width, height), config);

	}
}

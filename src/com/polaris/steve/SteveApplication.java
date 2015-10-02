package com.polaris.steve;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import com.polaris.engine.Application;
import com.polaris.engine.Font;

public class SteveApplication extends Application
{

	public static Font defaultFont;

	@Override
	protected void update() {


	}

	@Override
	protected void render(double mouseX, double mouseY, double delta) {


	}

	@Override
	protected void init()
	{
		try
		{
			setGui(new GUIMainMenu(this));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected int getRefreshRate() {
		return 60;
	}

	@Override
	protected int getUpdateRate() {
		return 30;
	}

	@Override
	protected String getTitle() {
		return "Steve";		// top kek
	}

	public void createWindow()
	{
		window.setUndecorated(true);
		window.setDefault();
		Display.setInitialBackground(1, 1, 1);
		Display.setVSyncEnabled(true);
	}

}

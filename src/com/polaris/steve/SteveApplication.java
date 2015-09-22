package com.polaris.steve;

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
	protected void init() {
		setGui(new GUIMainMenu(this));
		
	}

	@Override
	protected int getRefreshRate() {
		return 60;
	}

	@Override
	protected int getUpdateRate() {
		return 60;
	}

	@Override
	protected String getTitle() {
		return "Steve";		// top kek
	}
	
	public void createWindow()
	{
		window.setUndecorated(true);
		window.setDefault();
	}
	
}

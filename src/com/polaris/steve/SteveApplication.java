package com.polaris.steve;

import java.awt.Canvas;

import javax.swing.JFrame;

import com.polaris.engine.Application;
import com.polaris.engine.WindowHelper;

public class SteveApplication extends Application{

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

	@Override
	protected void createWindow(JFrame window, Canvas canvas) {
		WindowHelper.createDefault(window);
		window.getContentPane().add(canvas);
	}
	
}

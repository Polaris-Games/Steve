package com.polaris.steve;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.polaris.engine.Application;
import com.polaris.engine.WindowHelper;

public class SteveApplication extends Application
{

	@Override
	public void init()
	{
		setGui(new GuiMainMenu(this));
	}

	@Override
	public void render(double mouseX, double mouseY, double delta)
	{
		
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public int getUpdateRate() 
	{
		return 30;
	}

	@Override
	public int getRefreshRate() 
	{
		return 60;
	}

	@Override
	public String getTitle() 
	{
		return "Steve the Game";
	}

	@Override
	public void createWindow(JFrame window, Canvas canvas)
	{
		window.getContentPane().add(canvas);
		
		//setResizable(true);
		//setVSync(true);
	}

}

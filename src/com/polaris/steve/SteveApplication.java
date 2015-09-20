package com.polaris.steve;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;

import com.polaris.engine.Application;
import com.polaris.engine.IApplication;
import com.polaris.engine.Render;

public class SteveApplication implements IApplication
{

	@Override
	public void init()
	{
		Application.setGui(new GuiMainMenu());
	}

	@Override
	public void render(Application app, double delta)
	{
		
	}

	@Override
	public void update(Application app)
	{
		
	}

	@Override
	public int getTickRate() 
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
	public void applyWindow(Window window, Canvas canvas)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int)(screen.width / 1.5), (int)(screen.height / 1.5));
		((JFrame)window).setUndecorated(true);
		window.setBackground(Color.BLACK);
		canvas.setBackground(Color.BLACK);
		((JFrame)window).getContentPane().add(canvas);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		((JFrame)window).setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		Application.setResizable(true);
		Application.setVSync(true);
	}

}

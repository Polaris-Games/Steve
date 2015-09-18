package com.polaris.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Timer extends Thread
{
	private Application application;
	protected float delta;
	private long lastTime = getTime();
	protected boolean isRunning = true;
	
	protected Timer(Application app, int tickRate)
	{
		application = app;
	}
	
	public void run()
	{
		try
		{
			Keyboard.create();
			Mouse.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		loop();
	}

	private void loop()
	{
		long time = getTime();
		while(isRunning)
		{
			if((getTime() - time) / 30f > 1f)
			{
				time = getTime();
				application.logicLoop();
			}
			try
			{
				Thread.sleep(1L);
			}
			catch (InterruptedException e) 
			{
				return;
			}
		}
		if(!isRunning)
		{
			Mouse.destroy();
			Keyboard.destroy();
			return;
		}
	}
	
	protected double getDelta()
	{
		long currentTime = getTime();
		delta = (currentTime - lastTime) / 30f;
		lastTime = currentTime;
		return delta;
	}
	
	private long getTime()
	{
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

}

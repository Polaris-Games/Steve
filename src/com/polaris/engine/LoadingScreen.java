package com.polaris.engine;

public abstract class LoadingScreen extends GUI
{

	public LoadingScreen(Application app) 
	{
		super(app);
	}
	
	public abstract void load();

}

package com.polaris.steve;

import com.polaris.engine.Application;
import com.polaris.engine.Camera2D;
import com.polaris.engine.GUI;

public class GUISteve extends GUI
{
	
	protected Steve theBall;
	protected Camera2D camera;
	protected Background theBackground;

	public GUISteve(Application app) 
	{
		super(app);
		theBall = new Steve(0, 0);
		camera = new Camera2D();
		theBackground = new Background(camera);
	}
	
	public GUISteve(GUISteve gui)
	{
		super(gui.application);
		theBall = gui.theBall;
		camera = gui.camera;
		theBackground = gui.theBackground;
	}

}

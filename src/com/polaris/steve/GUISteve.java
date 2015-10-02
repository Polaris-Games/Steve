package com.polaris.steve;

import java.io.IOException;

import com.polaris.engine.Application;
import com.polaris.engine.Camera2D;
import com.polaris.engine.GUI;
import com.polaris.engine.PhysicsEngine;

public class GUISteve extends GUI
{
	
	protected Steve theBall;
	protected Camera2D camera;
	protected Background theBackground;
	protected PhysicsEngine physicsEngine;

	public GUISteve(Application app) throws IOException 
	{
		super(app);
		theBall = new Steve(0, 0, Math.random(), Math.random(), Math.random());
		camera = new Camera2D();
		theBackground = new Background(camera);
		physicsEngine = new PhysicsEngine("default");
		physicsEngine.addEntity(theBall);
	}
	
	public GUISteve(GUISteve gui)
	{
		super(gui.application);
		theBall = gui.theBall;
		camera = gui.camera;
		theBackground = gui.theBackground;
		physicsEngine = gui.physicsEngine;
	}

}

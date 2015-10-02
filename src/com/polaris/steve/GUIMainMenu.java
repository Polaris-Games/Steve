package com.polaris.steve;

import static com.polaris.engine.Renderer.*;
import static com.polaris.engine.Helper.*;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.util.vector.Vector4f;

import com.polaris.engine.Application;
import com.polaris.engine.Camera2D;
import com.polaris.engine.Color4d;
import com.polaris.engine.GUI;
import com.polaris.engine.Helper;

public class GUIMainMenu extends GUISteve
{

	public GUIMainMenu(Application app) throws IOException
	{
		super(app);
	}
	
	@Override
	public void render(double x, double y, double delta)
	{
		gl2d();
		glDisable(GL_TEXTURE_2D);
		glColor(1, 1, 1, 1);
		glBegin();
		drawRect(0, 0, 1280, 720);
		glEnd();
		physicsEngine.update(delta);
		camera.set();
		theBackground.render(delta);
		theBall.getPosition().update(delta);
		theBall.render(delta);
		glDisable(GL_TEXTURE_2D);
		glColor(1, 0, .4, .7);
		glBegin();
		drawRect(0, 640, 1280, 720);
		glEnd();
	}
	
	@Override
	public void update(double x, double y)
	{
		camera.center(theBall);
		theBackground.update();
	}
	
	public int keyPressed(int keyID)
	{
		int i = super.keyPressed(keyID);
		if(i > 0)
			return i;
		return 0;
	}
	
	public boolean mouseClick(double x, double y, int mouseID)
	{
		if(mouseID == 0)
		{
			//theBall.setAction(null);
		}
		return false;
		
	}

}

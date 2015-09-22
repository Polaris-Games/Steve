package com.polaris.steve;

import static com.polaris.engine.Renderer.*;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.opengl.GL11.*;

import com.polaris.engine.Application;
import com.polaris.engine.Camera2D;
import com.polaris.engine.GUI;

public class GUIMainMenu extends GUISteve
{

	public GUIMainMenu(Application app)
	{
		super(app);
	}
	
	@Override
	public void render(double x, double y, double delta)
	{
		gl2d();
		theBackground.render(delta);
		theBall.render(delta);
		
		drawArc(320, 100, 300, 0, Math.PI, 100, 20);
	}
	
	@Override
	public void update(double x, double y)
	{
		theBackground.update();
		theBall.update();
	}
	
	public int keyPressed(int keyID)
	{
		int i = super.keyPressed(keyID);
		if(i > 0)
			return i;
		switch(keyID)
		{
		case KEY_RETURN:
		case KEY_SPACE:
			theBall.setActionOnBounce(new ChangeVelocityAction(10, 5));
		}
		return 0;
	}
	
	public boolean mouseClick(double x, double y, int mouseID)
	{
		if(mouseID == 0)
		{
			theBall.setActionOnBounce(new ChangeVelocityAction(10, 5));
		}
		return false;
		
	}

}

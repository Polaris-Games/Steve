package com.polaris.steve;

import static com.polaris.engine.Renderer.drawRect;
import static com.polaris.engine.Renderer.glBegin;
import static com.polaris.engine.Renderer.glBind;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

import com.polaris.engine.Entity;
import com.polaris.engine.Pos.Pos2DPhysics;

public class Steve extends Entity
{
	public final double WIDTH = 64, HEIGHT = 64;
	private double red = 1;
	private double green = 0d;
	private double blue = 1;
	
	private Action bounceAction, flightAction, fallAction, collideAction;
	
	public Steve(double x, double y, double red, double green, double blue)
	{
		/* TODO:
		 * RULES TO IMPLEMENT
		 * =================================================
		 * one color has to be above .5
		 * all three colors can't be w/ in a .1 difference
		 * one color has to be below .9
		 * =================================================
		 */
		
		super(new Pos2DPhysics(x, y));
		this.red = red;
		this.green = green;
		this.blue = blue;
		
	}
	

	@Override
	public void render(double delta)
	{
		glColor3d(red,green,blue);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBind("steve");
		glBegin();
		drawRect(position.getX(),position.getY(), position.getX() + WIDTH, position.getY() + HEIGHT,0,0,1,1);
		glEnd();
	}

	/**
	 * should handle whatever... kinda
	 */
	@Override
	public void update()
	{
		if (bounceAction != null && bounceAction.isCalled() && !bounceAction.call(this) && !bounceAction.reset())
		{
			bounceAction = null;
		}
		
		if (flightAction != null && flightAction.isCalled() && !flightAction.call(this) && !flightAction.reset())
		{
			flightAction = null;
		}
		
		if (fallAction != null && fallAction.isCalled() && !fallAction.call(this) && !fallAction.reset())
		{
			fallAction = null;
		}
		
		if (collideAction != null && collideAction.isCalled() && !collideAction.call(this) && !collideAction.reset())
		{
			collideAction = null;
		}
		
		
	}
	
	public void setActionOnBounce(Action action)
	{
		bounceAction = action;
		//action.passObject(green, green);
		// DEBUG CODE
		System.out.println(action.toString());
		position.setX(position.getX() + 8);
	}

}

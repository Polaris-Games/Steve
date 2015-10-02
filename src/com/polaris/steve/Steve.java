package com.polaris.steve;

import static com.polaris.engine.Renderer.drawRect;
import static com.polaris.engine.Renderer.glBegin;
import static com.polaris.engine.Renderer.glBind;
import static com.polaris.engine.Renderer.glColor;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

import com.polaris.engine.Block;
import com.polaris.engine.Entity;
import com.polaris.engine.Helper;
import com.polaris.engine.Pos.PosPhysics;
import com.polaris.engine.Rectangle;

public class Steve extends Entity
{
	private double red = 1d;
	private double green = 0d;
	private double blue = 1d;
	private double bounceVelocityX = 0;
	private double bounceVelocityY = 20;
	private double changeVelocityX = 0;
	private double changeVelocityY = 0;
	private int rollTicks = 0;
	
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
		super(new Rectangle(new PosPhysics(x, y, 10))); //new PosPhysics(x, y, 10)
		this.red = red;
		this.green = green;
		this.blue = blue;
	}


	@Override
	public void render(double delta)
	{
		super.render(delta);
		glColor(red, green, blue);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBind("steve");
		glBegin();
		double stretchX = 1;
		double stretchY = 1;
		drawRect(getX(), getY(), getX() + 64 * stretchX, getY() + 64 * stretchY,0,0,1,1);
		glEnd();
	}

	public void onEntityCollide(Entity otherEntity)
	{

	}

	public boolean onObjectCollide(Block otherObject)
	{
		getPosition().setVelocityY(-20);
		getPosition().setVelocityX(10);
		return false;
	}

	public void onMove(double x, double y)
	{

	}

	public void setBounceVelocity(double vX, double vY)
	{
		bounceVelocityX = vX;
		bounceVelocityY = vY;
	}


	@Override
	public void update()
	{
		rollTicks = Math.max(rollTicks - 1, 0);
	}

}

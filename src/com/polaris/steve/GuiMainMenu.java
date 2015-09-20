package com.polaris.steve;

import com.polaris.engine.GuiScreen;
import static org.lwjgl.opengl.GL11.*;
import com.polaris.engine.Render;

public class GuiMainMenu extends GuiScreen
{
	private float rotate = 0;
	public GuiMainMenu() 
	{
		super(null);
	}
	
	@Override
	public void render(double x, double y, double delta)
	{
		//Render.glColor(1, 1);
		glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(-5, 5, -5, 5, -2, 5);
	    glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		//Render.glBegin();
		glColor4f(1, 0, 0, 1);
        glRotatef(rotate+=.001, 1, 1, 1);
        glBegin(GL_QUADS);
        glColor3f(0.0f,1f,0.0f);          // Set The Color To Green
        glVertex3f( 1f, 1f,-1f);          // Top Right Of The Quad (Top)
        glVertex3f(-1f, 1f,-1f);          // Top Left Of The Quad (Top)
        glVertex3f(-1f, 1f, 1f);          // Bottom Left Of The Quad (Top)
        glVertex3f( 1f, 1f, 1f);          // Bottom Right Of The Quad (Top)
        glColor3f(1f,0.5f,0.0f);          // Set The Color To Orange
        glVertex3f( 1f,-1f, 1f);          // Top Right Of The Quad (Bottom)
        glVertex3f(-1f,-1f, 1f);          // Top Left Of The Quad (Bottom)
        glVertex3f(-1f,-1f,-1f);          // Bottom Left Of The Quad (Bottom)
        glVertex3f( 1f,-1f,-1f);          // Bottom Right Of The Quad (Bottom)
        glColor3f(1f,0.0f,0.0f);          // Set The Color To Red
        glVertex3f( 1f, 1f, 1f);          // Top Right Of The Quad (Front)
        glVertex3f(-1f, 1f, 1f);          // Top Left Of The Quad (Front)
        glVertex3f(-1f,-1f, 1f);          // Bottom Left Of The Quad (Front)
        glVertex3f( 1f,-1f, 1f);          // Bottom Right Of The Quad (Front)
        glColor3f(1f,1f,0.0f);          // Set The Color To Yellow
        glVertex3f( 1f,-1f,-1f);          // Bottom Left Of The Quad (Back)
        glVertex3f(-1f,-1f,-1f);          // Bottom Right Of The Quad (Back)
        glVertex3f(-1f, 1f,-1f);          // Top Right Of The Quad (Back)
        glVertex3f( 1f, 1f,-1f);          // Top Left Of The Quad (Back)
        glColor3f(0.0f,0.0f,1f);          // Set The Color To Blue
        glVertex3f(-1f, 1f, 1f);          // Top Right Of The Quad (Left)
        glVertex3f(-1f, 1f,-1f);          // Top Left Of The Quad (Left)
        glVertex3f(-1f,-1f,-1f);          // Bottom Left Of The Quad (Left)
        glVertex3f(-1f,-1f, 1f);          // Bottom Right Of The Quad (Left)
        glColor3f(1f,0.0f,1f);          // Set The Color To Violet
        glVertex3f( 1f, 1f,-1f);          // Top Right Of The Quad (Right)
        glVertex3f( 1f, 1f, 1f);          // Top Left Of The Quad (Right)
        glVertex3f( 1f,-1f, 1f);          // Bottom Left Of The Quad (Right)
        glVertex3f( 1f,-1f,-1f);          // Bottom Right Of The Quad (Right)
        //glEnd();
		//Render.drawRect(0, 0, 100, 100);
		//Render.drawGradientRect(0, 0, 200, 200, 1, 1, 1, 0, 0.7, 0, 0.5);
		glEnd();
	}

}

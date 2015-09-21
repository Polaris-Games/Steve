package com.polaris.steve;

import static com.polaris.engine.Renderer.gl3d;
import static com.polaris.engine.Renderer.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.input.Keyboard;

import com.polaris.engine.Application;
import com.polaris.engine.GUI;
import com.polaris.engine.Pos.Pos3D;
public class GUIMainMenu extends GUI{

	Pos3D ballPos = new Pos3D(5, 0, -10);	// position of the ball
	double XAccel = 1;	// acceleration multiplier
	double YAccel = 1; // y accel
	boolean needToSlowDown = false;		// quick workaround for momentum
	// Math.atan(ballPos.getY() / ballPos.getX()) * 180 / Math.PI should equal the angle
	public GUIMainMenu(Application app) {
		super(app);
	}
	
	@Override
	public void render(double x, double y, double delta){
		
		ballPos.update(delta);
		
		gl3d();	
		
		//glTranslated(0, 0,-10.5);
		glTranslated(ballPos.getX(),ballPos.getY(),-10);
		glRotated(45, 1, 1, 1);
		
	    
		
		glBegin();
		glColor3f(0.0f,1.0f,0.0f);    // Color Blue
	    glVertex3f( 1.0f, 1.0f,-1.0f);    // Top Right Of The Quad (Top)
	    glVertex3f(-1.0f, 1.0f,-1.0f);    // Top Left Of The Quad (Top)
	    glVertex3f(-1.0f, 1.0f, 1.0f);    // Bottom Left Of The Quad (Top)
	    glVertex3f( 1.0f, 1.0f, 1.0f);    // Bottom Right Of The Quad (Top)
	    glColor3f(1.0f,0.5f,0.0f);    // Color Orange
	    glVertex3f( 1.0f,-1.0f, 1.0f);    // Top Right Of The Quad (Bottom)
	    glVertex3f(-1.0f,-1.0f, 1.0f);    // Top Left Of The Quad (Bottom)
	    glVertex3f(-1.0f,-1.0f,-1.0f);    // Bottom Left Of The Quad (Bottom)
	    glVertex3f( 1.0f,-1.0f,-1.0f);    // Bottom Right Of The Quad (Bottom)
	    glColor3f(1.0f,0.0f,0.0f);    // Color Red    
	    glVertex3f( 1.0f, 1.0f, 1.0f);    // Top Right Of The Quad (Front)
	    glVertex3f(-1.0f, 1.0f, 1.0f);    // Top Left Of The Quad (Front)
	    glVertex3f(-1.0f,-1.0f, 1.0f);    // Bottom Left Of The Quad (Front)
	    glVertex3f( 1.0f,-1.0f, 1.0f);    // Bottom Right Of The Quad (Front)
	    glColor3f(1.0f,1.0f,0.0f);    // Color Yellow
	    glVertex3f( 1.0f,-1.0f,-1.0f);    // Top Right Of The Quad (Back)
	    glVertex3f(-1.0f,-1.0f,-1.0f);    // Top Left Of The Quad (Back)
	    glVertex3f(-1.0f, 1.0f,-1.0f);    // Bottom Left Of The Quad (Back)
	    glVertex3f( 1.0f, 1.0f,-1.0f);    // Bottom Right Of The Quad (Back)
	    glColor3f(0.0f,0.0f,1.0f);    // Color Blue
	    glVertex3f(-1.0f, 1.0f, 1.0f);    // Top Right Of The Quad (Left)
	    glVertex3f(-1.0f, 1.0f,-1.0f);    // Top Left Of The Quad (Left)
	    glVertex3f(-1.0f,-1.0f,-1.0f);    // Bottom Left Of The Quad (Left)
	    glVertex3f(-1.0f,-1.0f, 1.0f);    // Bottom Right Of The Quad (Left)
	    glColor3f(1.0f,0.0f,1.0f);    // Color Violet
	    glVertex3f( 1.0f, 1.0f,-1.0f);    // Top Right Of The Quad (Right)
	    glVertex3f( 1.0f, 1.0f, 1.0f);    // Top Left Of The Quad (Right)
	    glVertex3f( 1.0f,-1.0f, 1.0f);    // Bottom Left Of The Quad (Right)
	    glVertex3f( 1.0f,-1.0f,-1.0f);    // Bottom Right Of The Quad (Right)
	    glEnd();            // End Drawing The Cube - See more at: http://www.codemiles.com/c-opengl-examples/draw-3d-cube-using-opengl-t9018.html#sthash.uD5lm0OP.dpuf
	    
	    
	}
	
	@Override
	public int keyPressed(int keyID){
		int i = super.keyPressed(keyID);
		if (i > 0)
			return i;
		switch(keyID)
		{
		case Keyboard.KEY_A:
			ballPos.moveX(-1);
			return 3;
		case Keyboard.KEY_D:
			ballPos.moveX(1);
			return 3;
		case Keyboard.KEY_W:
			ballPos.moveY(1);
			return 3;
		case Keyboard.KEY_S:
			ballPos.moveY(-1);
			return 3;
		}
		return 0;
	}
	
	@Override
	public int keyHeld(int keyID, int called)
	{
		switch(keyID)
		{
		case Keyboard.KEY_A:
			ballPos.moveX((-1 - called) / 10d);
			return 1;
		case Keyboard.KEY_D:
			ballPos.moveX((1 + called) / 10d);
			return 1;
		case Keyboard.KEY_W:
			ballPos.moveY(1);
			return 1;
		case Keyboard.KEY_S:
			ballPos.moveY(-1);
			return 1;
		}
		return 0;
	}
	
	

}

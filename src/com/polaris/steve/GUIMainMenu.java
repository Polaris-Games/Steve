package com.polaris.steve;

import org.lwjgl.input.Keyboard;

import com.polaris.engine.Application;
import com.polaris.engine.GUI;
import com.polaris.engine.Pos;
import com.polaris.engine.Renderer;
import static com.polaris.engine.Renderer.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
public class GUIMainMenu extends GUI{

	Pos ballPos = new Pos(1,-10.5);	// position of the ball
	double XAccel = 1;	// acceleration multiplier
	double YAccel = 1; // y accel
	boolean needToSlowDown = false;		// quick workaround for momentum
	// Math.atan(ballPos.getY() / ballPos.getX()) * 180 / Math.PI should equal the angle
	public GUIMainMenu(Application app) {
		super(app);
	}
	
	@Override
	public void render(double x, double y, double delta){
		gl3d();	
		
		//glTranslated(0, 0,-10.5);
		glTranslated(ballPos.getX(),0,ballPos.getY());
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
			ballPos.setX(ballPos.getX() + 1);
			System.out.println(XAccel);
			return 3;
		case Keyboard.KEY_D:
			ballPos.setX(ballPos.getX() - 1);
			System.out.println(XAccel);
			return 3;
		case Keyboard.KEY_W:
			ballPos.setY(ballPos.getY() + 1);
			System.out.println(YAccel);
			return 3;
		case Keyboard.KEY_S:
			ballPos.setY(ballPos.getY() - 1);
			System.out.println(YAccel);
			return 3;
		}
		return 0;
	}
	
	@Override
	public int keyHeld(int keyID){
		int i = super.keyHeld(keyID);
		if (i > 0)
			return i;
		switch(keyID)
		{
		case Keyboard.KEY_A:
			//ballPos.setX(ballPos.getX() + 1*(XAccel++));	// temporary acceleration
			ballPos.setX(ballPos.getX() + 1*(XAccel));		// w/o accel
			System.out.println(XAccel);
			//needToSlowDown = true;
			return 3;
		case Keyboard.KEY_D:
			//ballPos.setX(ballPos.getX() + 1*(XAccel--));	// temporary acceleration
			ballPos.setX(ballPos.getX() - 1*(XAccel));		// w/o accel
			System.out.println(XAccel);
			//needToSlowDown = true;
			return 3;
		case Keyboard.KEY_W:
			//ballPos.setY(ballPos.getY() + 1*(YAccel++));
			ballPos.setY(ballPos.getY() + 1*(YAccel));		
			System.out.println(YAccel);
			return 3;
		case Keyboard.KEY_S:
			//ballPos.setY(ballPos.getY() + 1*(YAccel--));
			ballPos.setY(ballPos.getY() - 1*(YAccel));
			System.out.println(YAccel);
			return 3;
		}
		
		return 0;
	}
	
	

}

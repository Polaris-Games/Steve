package com.polaris.engine;

import static com.polaris.engine.Render.*;
import static org.lwjgl.opengl.GL11.glEnd;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LWJGLFont extends Font
{
	
	private int textureId = 0;
	private int textureWidth = 0;
	private int textureHeight = 0;
	
	public LWJGLFont() {}
	
	protected LWJGLFont(IntObject[] charArray, String name)
	{
		super(charArray, name);
	}

	@Override
	protected void startDrawing()
	{
		glBegin();
	}

	@Override
	protected void drawQuad(double x, double y, double x1, double y1, float u, float v, float u1, float v1) 
	{
		drawImage(x, y, x1, y1, u, v, u1, v1);
	}
	
	@Override
	protected void draw()
	{
		glEnd();
	}

	@Override
	protected void loadTexture(InputStream textureStream)
	{
		try 
		{
			BufferedImage image = ImageIO.read(textureStream);
			textureWidth = image.getWidth();
			textureHeight = image.getHeight();
			textureId = createTextureId(getTextureName(), image, false);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void bind()
	{
		glBind(textureId);
	}

	@Override
	protected float getTextureWidth()
	{
		return textureWidth;
	}

	@Override
	protected float getTextureHeight()
	{
		return textureHeight;
	}
	
	public void destroy()
	{
		
	}
	
}

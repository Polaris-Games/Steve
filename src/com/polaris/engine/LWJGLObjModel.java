package com.polaris.engine;

import java.io.IOException;	

public class LWJGLObjModel extends ObjModel
{

	public LWJGLObjModel(String name)
	{
		super(name);
	}

	public LWJGLObjModel(String url, String textureUrl) throws IOException
	{
		super(url, textureUrl);
	}
	
	@Override
	public void render(double x, double y, double z, double rotationX, double rotationY, double rotationZ) 
	{
		
	}
	
	public void destroy()
	{
		
	}

}

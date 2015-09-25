package com.polaris.engine;

import com.polaris.engine.Pos.Pos2DPhysics;

public abstract class Entity
{
	protected Pos2DPhysics position;
	
	public Entity(Pos2DPhysics pos) 
	{
		position = pos;
	}
	
	public abstract void render(double delta);
	public abstract void update();
	
}

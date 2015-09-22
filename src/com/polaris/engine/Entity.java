package com.polaris.engine;

public abstract class Entity
{
	protected Pos position;
	
	public Entity(Pos pos) 
	{
		position = pos;
	}
	
	public abstract void render(double delta);
	public abstract void update();
	
}

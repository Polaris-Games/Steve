package com.polaris.engine;

import com.polaris.engine.Pos.PosPhysics;

public abstract class Entity
{

	protected Shape entityShape;
	protected int ticksAlive = 0;
	
	public Entity(Shape shape) 
	{
		entityShape = shape;
	}
	
	public void render(double delta)
	{
		getPosition().update(delta);
	}
	
	public void update()
	{
		ticksAlive++;
		entityShape.update();
	}
	
	public void onEntityCollide(Entity otherEntity) {}
	
	public boolean onObjectCollide(Block otherObject) {return false;}

	public void onMove(double x, double y) {}
	
	public PosPhysics getPosition()
	{
		return entityShape.position;
	}
	
	public Shape getEntityBounds()
	{
		return entityShape;
	}
	
	public void kill()
	{
		
	}
	
	public double getX()
	{
		return getPosition().getX();
	}
	
	public double getY()
	{
		return getPosition().getY();
	}
	
}

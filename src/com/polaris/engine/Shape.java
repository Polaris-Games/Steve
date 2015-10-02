package com.polaris.engine;

import com.polaris.engine.Pos.PosPhysics;

public abstract class Shape 
{
	
	protected PosPhysics position;
	
	public Shape(PosPhysics pos)
	{
		position = pos;
	}

	public void update()
	{
		
	}

	public abstract boolean inBounds(Shape entityBounds);
	public abstract Shape shift(double newX, double newY);
	public abstract double shiftX(Shape entityBounds, Shape shape);
	public abstract double shiftY(Shape entityBounds, Shape shape);

}

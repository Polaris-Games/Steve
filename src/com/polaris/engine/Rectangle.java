package com.polaris.engine;

import com.polaris.engine.Pos.PosPhysics;

public class Rectangle extends Shape
{

	public Rectangle(PosPhysics pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean inBounds(Shape entityBounds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Shape shift(double newX, double newY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double shiftX(Shape entityBounds, Shape shape) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double shiftY(Shape entityBounds, Shape shape) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}

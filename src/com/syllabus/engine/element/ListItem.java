package com.syllabus.engine.element;

public abstract class ListItem<T>
{

	private T value;
	protected boolean highlighted = false;
	protected double posX = 0;
	protected double posY = 0;
	protected double width = 0;
	protected double height = 0;
	
	public ListItem(T val)
	{
		value = val;
	}
	
	public T getValue()
	{
		return value;
	}
	
	protected void setValue(T val)
	{
		value = val;
	}
	
	public void setDimensions(double x, double y, double w, double h)
	{
		posX = x;
		posY = y;
		width = w;
		height = h;
	}
	
	public void update(double x, double y)
	{
		highlighted = isInRegion(x, y);
	}
	
	public abstract void render(double x, double y, double delta);
	
	public boolean isInRegion(double x, double y)
	{
		return x >= posX && y >= posY && x <= (posX + width) && y <= (posY + height);
	}
}

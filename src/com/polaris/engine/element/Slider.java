package com.polaris.engine.element;

public abstract class Slider<T> extends Element
{
	
	protected T minValue;
	protected T maxValue;
	protected T currentValue;
	
	public Slider(double x, double y, double width, double height, T min, T max, T current)
	{
		super(x, y, width, height);
		minValue = min;
		maxValue = max;
		currentValue = current;
	}
	
	public Slider(boolean visible, double x, double y, double width, double height, T min, T max, T current)
	{
		super(visible, x, y, width, height);
		minValue = min;
		maxValue = max;
		currentValue = current;
	}

	@Override
	public void update(double x, double y) 
	{
		
	}

	@Override
	public void render(double x, double y, double delta)
	{
		
	}

	@Override
	public boolean mouseClick(double x, double y, int mouseId) 
	{
		return false;
	}

}

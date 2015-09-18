package com.syllabus.engine.element;

public abstract class Slider extends Element
{
	
	protected Object minValue;
	
	protected Object maxValue;
	
	protected Object currentValue;
	
	public Slider(double x, double y, double width, double height, Object min, Object max, Object current)
	{
		super(x, y, width, height);
	}
	
	public Slider(boolean visible, double x, double y, double width, double height, Object min, Object max, Object current)
	{
		super(visible, x, y, width, height);
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

package com.polaris.engine.element;


public abstract class Button extends Element
{
	
	protected String buttonText;
	protected boolean highlighted = false;
	
	public Button(String name, double x, double y, double width, double height)
	{
		super(x, y, width, height);
		buttonText = name;
	}
	
	public Button(String name, boolean visible, double x, double y, double width, double height)
	{
		super(visible, x, y, width, height);
		buttonText = name;
	}

	@Override
	public void update(double x, double y)
	{
		super.update(x, y);
		highlighted = isInRegion(x, y);
	}

}

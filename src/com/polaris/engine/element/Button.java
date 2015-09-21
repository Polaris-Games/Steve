package com.polaris.engine.element;


public abstract class Button extends Element
{
	
	protected String buttonText;

	
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
	
}

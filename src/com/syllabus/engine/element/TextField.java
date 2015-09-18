package com.syllabus.engine.element;

public class TextField extends Element
{

	protected String text;
	protected String nullText = "";
	
	public TextField(double x, double y, double width, double height)
	{
		super(x, y, width, height);
	}
	
	public TextField(boolean visible, double x, double y, double width, double height)
	{
		super(visible, x, y, width, height);
	}
	
	public TextField(double x, double y, double width, double height, String text)
	{
		super(x, y, width, height);
		nullText = text;
	}
	
	public TextField(boolean visible, double x, double y, double width, double height, String text)
	{
		super(visible, x, y, width, height);
		nullText = text;
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

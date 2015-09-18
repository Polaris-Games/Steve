package com.syllabus.engine.element;

public abstract class MultiButton extends Button
{

	protected String[] modeNames;
	protected int mode = 0;

	public MultiButton(double x, double y, double width, double height, String ... list)
	{
		super(list[0], x, y, width, height);
		modeNames = list;
	}

	public MultiButton(boolean visible, double x, double y, double width, double height, String ... list)
	{
		super(list[0], visible, x, y, width, height);
		modeNames = list;
	}

	@Override
	public void update(double x, double y)
	{
		super.update(x, y);
	}

	@Override
	public boolean mouseClick(double x, double y, int mouseId)
	{
		mode++;
		if(mode >= modeNames.length)
		{
			mode = 0;
		}
		buttonText = modeNames[mode];
		return false;
	}

	public int getMode()
	{
		return mode;
	}

	public void setMode(int m)
	{
		mode = m;
		if(mode >= modeNames.length)
		{
			mode = 0;
		}
		buttonText = modeNames[mode];
	}
	
}

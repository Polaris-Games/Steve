package com.polaris.engine;

public class Color4d 
{
	
	private double r;
	private double g;
	private double b;
	private double a;
	
	public Color4d(double r, double g, double b, double a)
	{
		setColor(r, g, b, a);
	}
	
	public Color4d(Color4d color)
	{
		setColor(color);
	}
	
	public double getRed()
	{
		return r;
	}
	
	public void setRed(double r) 
	{
		this.r = r;
	}
	
	public double getGreen()
	{
		return g;
	}
	
	public void setGreen(double g) 
	{
		this.g = g;
	}
	
	public double getBlue()
	{
		return b;
	}
	
	public void setBlue(double b)
	{
		this.b = b;
	}
	
	public double getAlpha()
	{
		return a;
	}
	
	public void setAlpha(double a)
	{
		this.a = a;
	}

	public void setColor(Color4d color) 
	{
		setRed(color.getRed());
		setGreen(color.getGreen());
		setBlue(color.getBlue());
		setAlpha(color.getAlpha());
	}
	
	public void setColor(double r, double g, double b, double a)
	{
		setRed(r);
		setGreen(g);
		setBlue(b);
		setAlpha(a);
	}

}

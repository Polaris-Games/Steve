package com.polaris.engine;

import java.awt.Canvas;
import java.awt.Window;

public interface IApplication 
{
	
	public void init();
	
	public void render(Application app, double delta);
	
	public void update(Application app);
	
	public int getTickRate();
	
	public int getRefreshRate();

	public String getTitle();

	public void applyWindow(Window window, Canvas canvas);

}

package com.polaris.engine.element;

import com.polaris.engine.Gui;
import com.polaris.engine.Pos;

public abstract class Element 
{
	
	protected boolean visible = true;
	protected Pos pos;
	protected double elementWidth = 0;
	protected double elementHeight = 0;
	protected int elementId = 0;
	protected int ticksExisted = 0;
	protected Gui gui;
	
	public Element(double x, double y, double width, double height)
	{
		pos = new Pos(x, y);
		elementWidth = width;
		elementHeight = height;
	}
	
	public Element(boolean vis, double x, double y, double width, double height)
	{
		visible = vis;
		pos = new Pos(x, y);
		elementWidth = width;
		elementHeight = height;
	}

	public void update(double x, double y)
	{
		ticksExisted++;
	}
	
	public abstract void render(double x, double y, double delta);
	
	public abstract boolean mouseClick(double x, double y, int mouseId);
	
	public boolean mouseHeld(double x, double y, int mouseId) {return false;}
	
	public void mouseRelease(double x, double y, int mouseId) {}
	
	public void mouseScroll(double x, double y, int mouseMove) {}
	
	public void mouseOutOfRegion(double x, double y, int mouseId) {}
	
	public int keyPressed(int keyId) {return 0;}
	
	public int keyHeld(int keyId) {return 0;}
	
	public void keyRelease(int keyId) {}
	
	public boolean isInRegion(double x, double y)
	{
		return x >= pos.getX() && y >= pos.getY() && x <= (pos.getX() + elementWidth) && y <= (pos.getY() + elementHeight);
	}

	public void setId(int id) {elementId = id;}
	
	public int getId() {return elementId;}
	
	public boolean equals(Element e) {return getId() == e.getId();}
	
	public void preRender(double x, double y, double delta) {}
	
	public void postRender(double x, double y, double delta) {}
	
	public void setGui(Gui g) {gui = g;}
	
	public Gui getGui() {return gui;}
	
	public void setVisible(boolean r) {visible = r;}
	
	public boolean isVisible() {return visible;}
	
	public void close() {}

	public void unbind() {}
}

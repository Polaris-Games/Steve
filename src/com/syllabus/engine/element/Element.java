package com.syllabus.engine.element;

import com.syllabus.engine.Gui;

public abstract class Element 
{
	
	protected boolean render = true;
	protected double posX = 0;
	protected double posY = 0;
	protected double elementWidth = 0;
	protected double elementHeight = 0;
	protected int elementId = 0;
	protected int ticksExisted = 0;
	protected Gui gui;
	
	public Element(double x, double y, double width, double height)
	{
		posX = x;
		posY = y;
		elementWidth = width;
		elementHeight = height;
	}
	
	public Element(boolean visible, double x, double y, double width, double height)
	{
		render = visible;
		posX = x;
		posY = y;
		elementWidth = width;
		elementHeight = height;
	}

	public abstract void update(double x, double y);
	
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
		return x >= posX && y >= posY && x <= (posX + elementWidth) && y <= (posY + elementHeight);
	}

	public void setId(int id) {elementId = id;}
	
	public int getId() {return elementId;}
	
	public boolean equals(Element e) {return getId() == e.getId();}
	
	public void preRender(double x, double y, double delta) {}
	
	public void postRender(double x, double y, double delta) {}
	
	public void setGui(Gui g) {gui = g;}
	
	public Gui getGui() {return gui;}
	
	public void setRender(boolean r) {render = r;}
	
	public boolean isRendered() {return render;}
	
	public void close() {}

	public void unbind() {}
}

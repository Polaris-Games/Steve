package com.polaris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.polaris.engine.element.Element;

public abstract class GUI
{

	private volatile List<Element> elementList = Collections.synchronizedList(new ArrayList<Element>());
	protected Element currentElement;
	protected int ticksExisted = 0;
	protected boolean shiftDown = false;
	protected Application application;
	protected GUI parent;

	public GUI(Application app)
	{
		application = app;
		parent = null;
	}
	public GUI(Application app, GUI gui)
	{
		this(app);
		parent = gui;
	}
	
	public void init() {}

	public void update(double x, double y)
	{
		ticksExisted++;
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				e.update(x, y);
			}
		}
		catch(Exception e) {}
	}

	public void render(double x, double y, double delta)
	{
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isVisible())
					e.preRender(x, y, delta);
			}
		}
		catch(Exception e) {}
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isVisible())
					e.render(x, y, delta);
			}
		}
		catch(Exception e) {}
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isVisible())
					e.postRender(x, y, delta);
			}
		}
		catch(Exception e) {}
	}

	public boolean mouseClick(double x, double y, int mouseId)
	{
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isInRegion(x, y))
				{
					boolean flag = e.mouseClick(x, y, mouseId);
					if(flag && e != currentElement)
					{
						unbindCurrentElement(e);
					}
					elementUpdate(e);
					return flag;
				}
				else
				{
					e.mouseOutOfRegion(x, y, mouseId);
				}
			}
		}
		catch(Exception e) {}
		unbindCurrentElement();
		return false;
	}

	public boolean mouseHeld(double x, double y, int mouseId)
	{
		if(currentElement != null)
		{
			return currentElement.mouseHeld(x, y, mouseId);
		}
		return false;
	}

	public void mouseRelease(double x, double y, int mouseId)
	{
		if(currentElement != null)
		{
			currentElement.mouseRelease(x, y, mouseId);
			unbindCurrentElement();
		}
	}

	public void mouseScroll(double x, double y, int mouseMove) 
	{
		if(currentElement != null)
		{
			currentElement.mouseScroll(x, y, mouseMove);
		}
	}

	public int keyPressed(int keyID) 
	{
		if(currentElement != null)
		{
			return currentElement.keyPressed(keyID);
		}
		if(keyID == Keyboard.KEY_LSHIFT || keyID == Keyboard.KEY_RSHIFT)
		{
			shiftDown = true;
			return -1;
		}
		if(keyID == -1)
		{
			if(keyID == Keyboard.KEY_ESCAPE)
			{
				if(shiftDown)
				{
					application.setFullscreen(!application.isFullscreen());
					shiftDown = false;
				}
				else
				{
					if(getParent() != null)
					{
						getParent().reinit();
						application.setGui(getParent());
						return 0;
					}
					else
					{
						application.close();
					}
				}
			}
		}
		return -1;
	}

	public int keyHeld(int keyID)
	{
		if(currentElement != null)
		{
			return currentElement.keyHeld(keyID);
		}
		return -1;
	}

	public void keyRelease(int keyID)
	{
		if(currentElement != null)
		{
			currentElement.keyRelease(keyID);
		}
		if(keyID == Keyboard.KEY_LSHIFT || keyID == Keyboard.KEY_RSHIFT)
		{
			shiftDown = false;
		}
	}

	public void unbindCurrentElement(Element e)
	{
		unbindCurrentElement();
		currentElement = e;
	}

	public void unbindCurrentElement()
	{
		if(currentElement != null)
		{
			currentElement.unbind();
			currentElement = null;
		}
	}

	public void addElement(Element e)
	{
		e.setId(elementList.size());
		e.setGui(this);
		elementList.add(e);
	}

	public void removeElement(int i)
	{
		elementList.remove(i).close();
	}

	public void removeElements(int i, int i1)
	{
		for(int j = i1 - 1; j >= i; j--)
		{
			elementList.remove(j).close();
		}
	}

	public void setVisible(boolean visible, int i, int j)
	{
		for(int k = i; k < j; k++)
		{
			elementList.get(k).setVisible(visible);
		}
	}

	public Element getElement(int i)
	{
		return elementList.get(i);
	}

	public int getSize()
	{
		return elementList.size();
	}

	public void elementUpdate(Element e) {}

	public void clear()
	{
		elementList.clear();
	}

	protected void reinit()
	{
		
	}
	
	public void close() 
	{
		this.currentElement = null;
	}

	public Element getCurrentElement()
	{
		return currentElement;
	}
	
	protected GUI getParent()
	{
		return parent;
	}

}

package com.polaris.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.polaris.engine.element.Element;

public abstract class Gui
{

	private volatile List<Element> elementList = Collections.synchronizedList(new ArrayList<Element>());
	protected Element currentElement;
	protected int ticksExisted = 0;
	protected boolean shiftDown = false;

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
				if(e.isRendered())
					e.preRender(x, y, delta);
			}
		}
		catch(Exception e) {}
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isRendered())
					e.render(x, y, delta);
			}
		}
		catch(Exception e) {}
		try
		{
			for(int i = 0; i < elementList.size(); i++)
			{
				Element e = elementList.get(i);
				if(e.isRendered())
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

	public int keyPressed(int keyId) 
	{
		if(currentElement != null)
		{
			return currentElement.keyPressed(keyId);
		}
		if(keyId == Keyboard.KEY_LSHIFT || keyId == Keyboard.KEY_RSHIFT)
		{
			shiftDown = true;
			return -1;
		}
		return -1;
	}

	public int keyHeld(int keyId)
	{
		if(currentElement != null)
		{
			return currentElement.keyHeld(keyId);
		}
		return -1;
	}

	public void keyRelease(int keyId)
	{
		if(currentElement != null)
		{
			currentElement.keyRelease(keyId);
		}
		if(keyId == Keyboard.KEY_LSHIFT || keyId == Keyboard.KEY_RSHIFT)
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
			elementList.get(k).setRender(visible);
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

	public void close() {}

	public Element getCurrentElement()
	{
		return currentElement;
	}

}

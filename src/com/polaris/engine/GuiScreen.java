package com.polaris.engine;

import org.lwjgl.input.Keyboard;

import static com.polaris.engine.Application.*;

public class GuiScreen extends Gui
{

	public GuiScreen parentScreen = null;

	public GuiScreen(GuiScreen screen)
	{
		parentScreen = screen;
	}

	public int keyPressed(int keyId) 
	{
		int key = super.keyPressed(keyId);
		if(key == -1)
		{
			if(keyId == Keyboard.KEY_ESCAPE)
			{
				if(shiftDown)
				{
					setFullscreen(!getFullscreen());
					shiftDown = false;
				}
				else
				{
					if(getParent() != null)
					{
						getParent().reinit();
						setGui(getParent());
						return 0;
					}
					else
					{
						Application.close();
					}
				}
			}
		}
		return key;
	}
	
	public void reinit()
	{
		
	}
	
	@Override
	public void close()
	{
		super.close();
		this.currentElement = null;
	}
	
	protected GuiScreen getParent()
	{
		return parentScreen;
	}

}

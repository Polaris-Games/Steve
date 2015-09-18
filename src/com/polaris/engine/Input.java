package com.polaris.engine;

import static com.polaris.engine.Render.getMouseX;
import static com.polaris.engine.Render.getMouseY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input
{
	private static Map<Integer, Integer> keyboardPress = new HashMap<Integer, Integer>();
	private static List<Integer> mousePress = new ArrayList<Integer>();

	public static boolean isGrabbing = false;

	protected static void update(Gui gui)
	{
		for(Integer i : keyboardPress.keySet())
		{
			int j = keyboardPress.get(i);
			if(--j <= 0)
			{
				j = gui.keyHeld(i);
				if(j <= 0)
				{
					keyboardPress.remove(i);
				}
				else
				{
					keyboardPress.put(i, j);
				}
			}
			else
			{
				keyboardPress.put(i, j);
			}
		}
		while(Keyboard.next())
		{
			boolean b = keyboardPress.containsKey(Keyboard.getEventKey());
			boolean b1 = Keyboard.getEventKeyState();
			if(!b && b1)
			{
				int i = gui.keyPressed(Keyboard.getEventKey());
				if(i > 0)
				{
					keyboardPress.put(Integer.valueOf(Keyboard.getEventKey()), i);
				}
			}
			else if(!b1)
			{
				gui.keyRelease(Keyboard.getEventKey());
				if(b)
				{
					keyboardPress.remove(Integer.valueOf(Keyboard.getEventKey()));
				}
			}
		}
		for(int k = 0; k < mousePress.size(); k++)
		{
			Integer i = mousePress.get(k);
			boolean b = gui.mouseClick(getMouseX(), getMouseY(), i);
			if(!b)
			{
				mousePress.remove(i);
			}
		}
		while(Mouse.next())
		{
			boolean b = mousePress.contains(Mouse.getEventButton());
			boolean b1 = Mouse.getEventButtonState();
			if(!b && b1)
			{
				if(gui.mouseClick(getMouseX(), getMouseY(), Mouse.getEventButton()))
				{
					isGrabbing = true;
					mousePress.add(Integer.valueOf(Mouse.getEventButton()));
				}
			}
			else if(b && !b1)
			{
				gui.mouseRelease(getMouseX(), getMouseY(), Mouse.getEventButton());
				isGrabbing = false;
				mousePress.remove(Integer.valueOf(Mouse.getEventButton()));
			}
		}
		int i = Mouse.getDWheel();
		gui.mouseScroll(getMouseX(), getMouseY(), i > 0 ? 1 : i < 0 ? -1 : 0);
	}

}

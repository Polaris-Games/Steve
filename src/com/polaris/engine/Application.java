package com.polaris.engine;

import static com.polaris.engine.Renderer.*;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;


public abstract class Application 
{
	private JFrame window;
	private Gui gui;
	private Thread renderThread;
	private Thread logicThread;
	private boolean isRunning = true;
	private boolean fullscreen = false;
	private long lastTime = getTime();
	private Map<Integer, Integer> keyboardPress = new HashMap<Integer, Integer>();
	private List<Integer> mousePress = new ArrayList<Integer>();
	private SoundManager soundManager;

	public boolean isGrabbing = false;

	public Application()
	{
		renderThread = new Thread()
		{
			public void run()
			{
				logicThread = new Thread()
				{

					public void run()
					{
						try
						{
							Keyboard.create();
							Mouse.create();
						}
						catch(LWJGLException e)
						{
							e.printStackTrace();
						}
						loop();
					}

					private void loop()
					{
						long time = getTime();
						while(isRunning)
						{
							if((getTime() - time) / 30f > 1f)
							{
								time = getTime();
								logicLoop();
							}
							try
							{
								Thread.sleep(1L);
							}
							catch (InterruptedException e) 
							{
								return;
							}
						}
						Mouse.destroy();
						Keyboard.destroy();
					}
				};
				try
				{
					Canvas canvas = new Canvas();
					window = new JFrame(getTitle());

					createWindow(window, canvas);

					Display.setParent(canvas);
					Display.create();
				}
				catch(LWJGLException e)
				{
					e.printStackTrace();
				}

				soundManager = new SoundManager();

				glDefaults();

				init();
			}
		};
		renderThread.start();
	}

	public void startApplication()
	{
		logicThread.start();

		while(!Display.isCloseRequested() && isRunning)
		{
			glClearBuffers();
			setMousePosition();
			renderLoop();
			glUpdate(getRefreshRate());
		}

		shutdown();
	}

	private void logicLoop()
	{
		update();
		if(gui != null)
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
			gui.update(getMouseX(), getMouseY());
		}
	}

	private void renderLoop()
	{
		long currentTime = getTime();
		double delta = (currentTime - lastTime) / 30f;
		lastTime = currentTime;
		render(getMouseX(), getMouseY(), delta);
		if(fullscreen != Display.isFullscreen())
		{
			try 
			{
				Display.setFullscreen(fullscreen);
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		if(gui != null)
		{
			gui.render(getMouseX(), getMouseY(), delta);
		}
	}

	protected abstract void update();
	protected abstract void render(double mouseX, double mouseY, double delta);
	protected abstract void init();
	protected abstract int getRefreshRate();
	protected abstract int getUpdateRate();
	protected abstract String getTitle();
	protected abstract void createWindow(JFrame window, Canvas canvas);

	private void shutdown()
	{
		soundManager.flush();
		while(logicThread.isAlive());
		Display.destroy();
		AL.destroy();
		System.exit(1);
	}

	public void setIcons(String windowIcon, String barIcon)
	{
		try 
		{
			Display.setIcon(new ByteBuffer[]{
					loadIcon(windowIcon),
					loadIcon(barIcon)
			});
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private ByteBuffer loadIcon(String filename) throws IOException
	{
		BufferedImage image = ImageIO.read(Helper.getResourceStream(filename));
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for(int y = 0; y < image.getHeight(); y++)
		{
			for(int x = 0; x < image.getWidth(); x++)
			{
				int pixel = image.getRGB(x, y);
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();
		return buffer;
	}

	public void setGui(Gui newGui)
	{
		if(newGui == null)
		{
			close();
			return;
		}
		if(gui != null)
		{
			gui.close();
		}
		newGui.init();
		gui = newGui;
	}

	public void close()
	{
		isRunning = false;
	}

	public void setFullscreen(boolean f)
	{
		fullscreen = f;
	}

	public boolean isFullscreen()
	{
		return Display.isFullscreen();
	}

	private long getTime()
	{
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

}

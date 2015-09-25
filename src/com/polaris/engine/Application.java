package com.polaris.engine;

import static com.polaris.engine.Renderer.getMouseX;
import static com.polaris.engine.Renderer.getMouseY;
import static com.polaris.engine.Renderer.glClearBuffers;
import static com.polaris.engine.Renderer.glDefaults;
import static com.polaris.engine.Renderer.glUpdate;
import static com.polaris.engine.Renderer.setMousePosition;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;


public abstract class Application 
{
	private GUI gui;
	protected Window window;
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
							if((getTime() - time) / (float) getUpdateRate() > 1f)
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
					window = new Window(getTitle(), canvas);

					createWindow();

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
				startApplication();
			}
		};
		renderThread.start();
	}

	private void startApplication()
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
				int k = j & 0x0000FFFF;
				j >>= 16;
				if(k-- <= 0)
				{
					k = gui.keyHeld(i, j);
					j++;
					if(k <= 0)
					{
						keyboardPress.remove(i);
						continue;
					}
				}
				keyboardPress.put(i, (j << 16) | k);
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
			for(Integer i : mousePress)
			{
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
			gui.mouseScroll(getMouseX(), getMouseY(), Mouse.getDWheel());
			gui.update(getMouseX(), getMouseY());
		}
	}

	private void renderLoop()
	{
		long currentTime = getTime();
		double delta = (currentTime - lastTime) * getUpdateRate() / (1000d);
		lastTime = currentTime;
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
		render(getMouseX(), getMouseY(), delta);
	}

	/**
	 * After every iteration on the logic loop, this method is called prior to all other update methods.
	 * If the update is integral to the entire application, put stuff in here. Otherwise go with the GUI
	 * version of update.
	 */
	protected abstract void update();
	
	/**
	 * After every iteration on the render loop, this method is called last compared to all other render methods.
	 * Useful for overlays that are integral to the application.
	 * @param mouseX : current mouse position on the x-axis relative to the window and scaled to 1280
	 * @param mouseY : current mouse position on the y-axis relative to the window and scaled to 720
	 * @param delta : change in time relative to the logic loop.
	 */
	protected abstract void render(double mouseX, double mouseY, double delta);
	
	/**
	 * Initialization of the application, setGUI is vital here
	 */
	protected abstract void init();
	
	/**
	 * @return Number of times the game will render per second if possible. -1 for non restrictive
	 */
	public abstract int getRefreshRate();
	
	/**
	 * @return Number of times the game will update per second. Recommended to not push limits on updating.
	 */
	public abstract int getUpdateRate();
	
	/**
	 * @return Title of the application, non-applicable if undecorated
	 */
	public abstract String getTitle();
	
	/**
	 * Apply window effects here
	 */
	protected abstract void createWindow();

	private void shutdown()
	{
		soundManager.flush();
		while(logicThread.isAlive());
		Display.destroy();
		AL.destroy();
		System.exit(1);
	}

	/**
	 * Set the applications icons on the task bar and top left corner (if window is decorated)
	 * @param windowIcon : The top right window icon
	 * @param barIcon : The task bar icon
	 */
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

	/**
	 * Set the current GUI to this new instance, the old gui will call the close method.
	 * @param newGui : if null, game will close down. If not then init method will be called.
	 */
	public void setGui(GUI newGui)
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
		if(newGui instanceof LoadingScreen)
		{
			((LoadingScreen) newGui).load();
		}
		gui = newGui;
	}

	/**
	 * Close the game safely
	 */
	public void close()
	{
		isRunning = false;
	}

	/**
	 * Set the screen to fullscreen or not
	 * @param fullscreen : true for fullscreen, false otherwise
	 */
	public void setFullscreen(boolean f)
	{
		fullscreen = f;
	}

	/**
	 * Is the screen fullscreen
	 * @return if fullscreen then true, false otherwise
	 */
	public boolean isFullscreen()
	{
		return Display.isFullscreen();
	}

	private long getTime()
	{
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

}

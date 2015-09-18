package com.syllabus.engine;

import static com.syllabus.engine.Render.getMouseX;
import static com.syllabus.engine.Render.getMouseY;
import static com.syllabus.engine.Render.gl2d;
import static com.syllabus.engine.Render.gl3d;
import static com.syllabus.engine.Render.glClearBuffers;
import static com.syllabus.engine.Render.glDefaults;

import java.awt.Canvas;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;


public class Application 
{
	public static Application app;
	private static JFrame window;
	private IApplication appRunner;
	private Gui gui;
	private Timer timer;
	private static boolean isCloseRequested = false;
	private boolean is3d = false;
	private boolean fullscreen = false;

	public Application(IApplication runner)
	{
		app = this;
		appRunner = runner;
		timer = new Timer(this, appRunner.getTickRate());
		try
		{
			Canvas canvas = new Canvas();
			window = new JFrame(appRunner.getTitle());

			appRunner.applyWindow(window, canvas);

			Display.setParent(canvas);
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}

		Sound.init();

		updateScreen();

		glDefaults();

		runner.init();
	}

	public void startApplication()
	{
		timer.start();

		while(!Display.isCloseRequested() && !isCloseRequested)
		{
			glClearBuffers();
			renderLoop();
			Display.update();
			Display.sync(appRunner.getRefreshRate());
		}

		shutdown();
	}

	protected void logicLoop()
	{
		appRunner.update(this);
		if(gui != null)
		{
			Input.update(gui);
			gui.update(getMouseX(), getMouseY());
		}
	}

	protected void renderLoop()
	{
		double delta = timer.getDelta();
		appRunner.render(this, delta);
		if(fullscreen != Display.isFullscreen())
		{
			try 
			{
				Display.setFullscreen(fullscreen);
				updateScreen();
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

	public void updateScreen()
	{
		if(is3d)
		{
			gl3d();
		}
		else
		{
			gl2d();
		}
	}

	private void shutdown()
	{
		timer.isRunning = false;
		Sound.flush();
		while(timer.isAlive());
		Display.destroy();
		AL.destroy();
		System.exit(1);
	}

	public static void setDimensions(int width, int height)
	{
		window.setSize(width, height);
	}

	protected static void removeBorders()
	{
		window.setUndecorated(true);
	}

	public static void setResizable(boolean resize)
	{
		window.setResizable(resize);
	}

	public static void setTitle(String title)
	{
		window.setTitle(title);
	}

	public static void setVSync(boolean vSync)
	{
		Display.setVSyncEnabled(vSync);
	}

	public static void setFullscreen(boolean screen)
	{
		app.fullscreen = screen;
	}

	public static void setIcons(String windowIcon, String barIcon)
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

	private static ByteBuffer loadIcon(String filename) throws IOException
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

	public static void setGui(Gui newGui)
	{
		if(newGui == null)
		{
			close();
			return;
		}
		if(app.gui != null)
		{
			app.gui.close();
		}
		newGui.init();
		app.gui = newGui;
	}

	public static void close()
	{
		isCloseRequested = true;
	}

	public static boolean getFullscreen()
	{
		return Display.isFullscreen();
	}

}

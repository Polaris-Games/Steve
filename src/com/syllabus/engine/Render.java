package com.syllabus.engine;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DITHER;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBTextureStorage;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Render
{
	private static Map<String, Integer> textureList = new HashMap<String, Integer>();

	private static int currentTexture = -100;

	protected static int width = 1280;
	protected static int height = 720;
	protected static double widthProportional = 1;
	protected static double heightProportional = 1;

	public static int createTextureId(String filename, boolean mipmap)
	{
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(Helper.getResourceStream("textures/" + filename + (filename.lastIndexOf('.') == -1 ? ".png" : "")));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return createTextureId(filename, image, mipmap);
	}

	public static int createTextureId(String s, BufferedImage image, boolean mipmap)
	{
		if(image != null)
		{
			int texture = glGenTextures();
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
			glBindTexture(GL_TEXTURE_2D, texture);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			if(mipmap)
			{
				ARBTextureStorage.glTexStorage2D(GL_TEXTURE_2D, 5, GL_RGBA8, image.getWidth(), image.getHeight());
				glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, image.getWidth(), image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer);
				ARBFramebufferObject.glGenerateMipmap(GL_TEXTURE_2D);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
			}
			else
			{
				ARBTextureStorage.glTexStorage2D(GL_TEXTURE_2D, 1, GL_RGBA8, image.getWidth(), image.getHeight());
				glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, image.getWidth(), image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			}
			textureList.put(s, texture);
			return texture;
		}
		return -100;
	}

	public static void gl2d()
	{
		width = Display.getWidth();
		height = Display.getHeight();
		widthProportional = width / 1280d;
		heightProportional = height / 720d;

		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1280, 720, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDisable(GL_DEPTH_TEST);
	}

	public static void gl3d()
	{
		width = Display.getWidth();
		height = Display.getHeight();
		widthProportional = width / 1280d;
		heightProportional = height / 720d;

		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(90, (float) width / height, 0.1f, Float.MAX_VALUE);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	public static boolean glBind(int textureId)
	{
		if(textureId != currentTexture)
		{
			glBindTexture(GL_TEXTURE_2D, textureId);
			currentTexture = textureId;
			return true;
		}
		return false;
	}

	/**
	 * Binds the current OpenGl texture to be the [String:texture] and if it isn't cached already it will be cached
	 * @param texture : The texture to be bound
	 * @return Whether it was successfully bound or not
	 */
	public static boolean glBind(String texture)
	{
		int tex = textureList.containsKey(texture) ? textureList.get(texture) : createTextureId(texture, true);
		if(tex != -100 && tex != currentTexture)
		{
			glBindTexture(GL_TEXTURE_2D, tex);
			currentTexture = tex;
			return true;
		}
		return false;
	}

	public static boolean glBind(String texture, boolean mipmap)
	{
		int tex = textureList.containsKey(texture) ? textureList.get(texture) : createTextureId(texture, mipmap);
		if(tex != -100 && tex != currentTexture)
		{
			glBindTexture(GL_TEXTURE_2D, tex);
			currentTexture = tex;
			return true;
		}
		return false;
	}

	public static boolean glClear(String texture)
	{
		if(textureList.containsKey(texture))
		{
			GL11.glDeleteTextures(textureList.get(texture));
			textureList.remove(texture);
			return true;
		}
		return false;
	}

	/**
	 * Binds the current OpenGl texture to be the [String:textureName] from the cached and if non existent then it will generate
	 * the texture under the [BufferedImage:texture]
	 * @param name : The name in the cache to be bound
	 * @param texture : the BufferedImage to add to cache in case not there
	 * @return Whether it was successfully bound or not
	 */
	public static boolean glBind(String name, BufferedImage texture)
	{
		int texId = textureList.containsKey(name) ? textureList.get(name) : createTextureId(name, texture, true);
		if(texId != -100 && texId != currentTexture)
		{
			glBindTexture(GL_TEXTURE_2D, texId);
			currentTexture = texId;
			return true;
		}
		return false;
	}

	public static boolean glBind(String name, BufferedImage texture, boolean mipmap)
	{
		glEnable(GL_TEXTURE_2D);
		int texId = textureList.containsKey(name) ? textureList.get(name) : createTextureId(name, texture, mipmap);
		if(texId != -100 && texId != currentTexture)
		{
			glBindTexture(GL_TEXTURE_2D, texId);
			currentTexture = texId;
			return true;
		}
		return false;
	}

	public static void glEnableText()
	{
		glEnable(GL_TEXTURE_2D);
		glBlend();
	}

	public static void glBlend()
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void glBlend(int value, int value1)
	{
		glEnable(GL_BLEND);
		glBlendFunc(value, value1);
	}

	public static void glBegin()
	{
		GL11.glBegin(GL_QUADS);
	}

	public static void glClearBuffers()
	{
		GL11.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	}

	public static void glUpdate(int value)
	{
		Display.update();
		Display.sync(value);
	}

	public static void glDefaults()
	{
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glShadeModel(GL_SMOOTH);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1.0f);
		glClearStencil(0);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, .01f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glDisable(GL_DITHER);

		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);

		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		glColor3f(1.0f, 1.0f, 1.0f);
	}

	public static void vertexUV(double x, double y, double u, double v)
	{
		glTexCoord2d(u, v);
		glVertex2d(x, y);
	}

	public static void vertexUV(double x, double y, double z, double u, double v)
	{
		glTexCoord2d(u, v);
		glVertex3d(x, y, z);
	}

	public static void drawRect(double x, double y, double x1, double y1, double thickness)
	{
		glVertex2d(x, y1);
		glVertex2d(x1, y1);
		glVertex2d(x1, y1 - thickness);
		glVertex2d(x, y1 - thickness);
		glVertex2d(x1 - thickness, y1 - thickness);
		glVertex2d(x1, y1 - thickness);
		glVertex2d(x1, y);
		glVertex2d(x1 - thickness, y);
		glVertex2d(x, y + thickness);
		glVertex2d(x1 - thickness, y + thickness);
		glVertex2d(x1 - thickness, y);
		glVertex2d(x, y);
		glVertex2d(x, y1 - thickness);
		glVertex2d(x + thickness, y1 - thickness);
		glVertex2d(x + thickness, y + thickness);
		glVertex2d(x, y + thickness);
	}
	
	public static void drawRect(double x, double y, double x1, double y1)
	{
		glVertex2d(x, y1);
		glVertex2d(x1, y1);
		glVertex2d(x1, y);
		glVertex2d(x, y);
	}
	
	public static void glColor(double d, double alpha)
	{
		glColor4d(d, d, d, alpha);
	}
	
	public static void drawGradientRect(double x, double y, double x1, double y1, double a, double a1, double a2, double a3, double r, double g, double b)
	{
		glColor4d(r, g, b, a);
		glVertex2d(x, y1);
		glColor4d(r, g, b, a1);
		glVertex2d(x1, y1);
		glColor4d(r, g, b, a2);
		glVertex2d(x1, y);
		glColor4d(r, g, b, a3);
		glVertex2d(x, y);
	}

	public static void drawImage(double x, double y, double x1, double y1, double u, double v, double u1, double v1)
	{
		vertexUV(x, y1, u, v1);
		vertexUV(x1, y1, u1, v1);
		vertexUV(x1, y, u1, v);
		vertexUV(x, y, u, v);
	}

	public static double getWidthScale()
	{
		return widthProportional;
	}

	public static double getHeightScale()
	{
		return heightProportional;
	}
	
	public static double getMouseX()
	{
		return Mouse.getX() * widthProportional;
	}
	
	public static double getMouseY()
	{
		return 720 - (Mouse.getY() * heightProportional);
	}

}

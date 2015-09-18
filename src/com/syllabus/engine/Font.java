package com.syllabus.engine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class Font
{

	public static final byte FONT_ALIGN_CENTER = 0b00000001;
	public static final byte FONT_ALIGN_RIGHT = 0b00000010;
	public static final byte FONT_FIT_TO_REGION = 0b00000100;
	public static final byte FONT_LIFT_TEXT = 0b00001000;
	public static final byte FONT_SCALE = 0b00010000;
	public static final byte FONT_SHIFT_COLOR = 0b00100000;
	public static final byte FONT_SPLIT = 0b01000000;

	private IntObject[] charArray = new IntObject[256];
	private String fontName = "";

	public Font() {}

	protected Font(IntObject[] charArray, String name)
	{
		this.charArray = charArray;
		fontName = name;
		loadTexture(Helper.getResourceStream("fonts/" + name + ".png"));
	}

	public void initialize(String name)
	{
		try 
		{
			fontName = name;
			initialize(Helper.getResourceStream("fonts/" + name + ".png"), Helper.getResourceStream("fonts/" + name + ".fnt"));
		}
		catch (IOException | SAXException | ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
	}

	public void initialize(String textureUrl, String fontUrl) throws IOException
	{
		HttpURLConnection textureConnection = (HttpURLConnection) new URL(textureUrl).openConnection();
		HttpURLConnection fontConnection = (HttpURLConnection) new URL(fontUrl).openConnection();
		textureConnection.connect();
		fontConnection.connect();
		try
		{
			initialize(textureConnection.getInputStream(), fontConnection.getInputStream());
			textureConnection.disconnect();
			fontConnection.disconnect();
		}
		catch(SAXException | ParserConfigurationException e)
		{
			textureConnection.disconnect();
			fontConnection.disconnect();
			e.printStackTrace();
		}
	}

	private void initialize(InputStream textureStream, InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlStream);

		Element fontNode = (Element) doc.getElementsByTagName("font").item(0);
		fontName = fontNode.getAttribute("id");
		loadTexture(textureStream);

		NodeList list = fontNode.getChildNodes();

		for(int i = 0; i < list.getLength(); i++)
		{
			Node n = list.item(i);

			if(n.getNodeType() == Node.ELEMENT_NODE)
			{
				Element e = (Element) n;
				short id = (short) Integer.parseInt(e.getAttribute("id"));
				charArray[id] = new IntObject();
				charArray[id].width = Short.parseShort(e.getAttribute("xadvance"));
				charArray[id].height = (short) (Short.parseShort(e.getAttribute("height")) + Short.parseShort(e.getAttribute("yoffset")));
				charArray[id].offsetX = (short) (Short.parseShort(e.getAttribute("xoffset")));
				charArray[id].offsetY = (short) (Short.parseShort(e.getAttribute("yoffset")));
				charArray[id].storedX = Integer.parseInt(e.getAttribute("x")) / (float)getTextureWidth();
				charArray[id].storedY = Integer.parseInt(e.getAttribute("y")) / (float)getTextureHeight();
				charArray[id].storedX1 = charArray[id].storedX + (Integer.parseInt(e.getAttribute("width")) / (float) getTextureWidth());
				charArray[id].storedY1 = charArray[id].storedY + (Integer.parseInt(e.getAttribute("height")) / (float) getTextureHeight());
			}
		}
	}

	public void drawString(String text, double x, double y)
	{
		drawString(text, x, y, 0);
	}

	public void drawString(String text, double x, double y, int modifiers, Object ... modObjects)
	{
		IntObject intObject;
		short i;
		short letter;
		double x1 = x;
		double y1 = y;
		byte opDefines = 0;
		double widthScale = 1;
		double heightScale = 1;

		//FIT TO REGION
		if(((modifiers >> 2) & 1) == 1)
		{
			widthScale = (double) modObjects[opDefines] / (double) getTextWidth(text);
			opDefines++;
			heightScale = (double) modObjects[opDefines] / (double) getHeight(text);
			opDefines++;
			widthScale = heightScale = Math.min(widthScale, heightScale);
		}
		//LIFT TEXT
		if(((modifiers >> 3) & 1) == 1)
		{

		}
		//SCALE
		if(((modifiers >> 4) & 1) == 1)
		{
			widthScale = Math.min((double) modObjects[opDefines], widthScale);
			opDefines++;
			heightScale = Math.min((double) modObjects[opDefines], heightScale);
			opDefines++;
		}
		//SHIFT COLOR
		if(((modifiers >> 5) & 1) == 1)
		{
			
		}
		//SPLIT
		if(((modifiers >> 6) & 1) == 1)
		{
			
		}
		//CENTER
		if((modifiers & 1) == 1)
		{
			x1 = x - (getTextWidth(text) / 2) * widthScale;
			y1 = y - (getHeight(text) / 2) * heightScale;
		}
		//ALIGN RIGHT
		else if(((modifiers >> 1) & 1) == 1)
		{
			x1 = x - getTextWidth(text) * widthScale;
		}
		
		bind();
		startDrawing();
		for(i = 0; i < text.length(); i++)
		{
			letter = (short) text.charAt(i);
			if(letter < 256 && charArray[letter] != null)
			{
				intObject = charArray[letter];
				drawQuad(x1 + (intObject.offsetX) * widthScale, y1 + (intObject.offsetY) * heightScale, x1 + (intObject.offsetX + intObject.width) * widthScale, y1 + (intObject.height) * heightScale, intObject.storedX, intObject.storedY, intObject.storedX1, intObject.storedY1);
				x1 += (intObject.width + intObject.offsetX) * widthScale;
			}
		}
		draw();
	}

	public int getTextWidth(CharSequence whatchars)
	{
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;

		for (int i = 0; i < whatchars.length(); i++)
		{
			currentChar = whatchars.charAt(i);
			if (currentChar < 256)
			{
				intObject = charArray[currentChar];
				totalwidth += intObject.width + intObject.offsetX;
			}
		}
		return totalwidth;
	}

	public double getTextWidth(CharSequence whatchars, double widthScale)
	{
		return getTextWidth(whatchars) * widthScale;
	}

	public int getHeight(CharSequence whatchars)
	{
		int height = 0;
		IntObject intObject = null;
		int currentChar = 0;

		for (int i = 0; i < whatchars.length(); i++)
		{
			currentChar = whatchars.charAt(i);
			if (currentChar < 256)
			{
				intObject = charArray[currentChar];
				height = Math.max(intObject.height, height);
			}
		}
		return height;
	}
	
	public String getTextureName()
	{
		return fontName;
	}

	protected abstract void startDrawing();
	protected abstract void drawQuad(double x, double y, double x1, double y1, float u, float v, float u1, float v1);
	protected abstract void draw();
	protected abstract void loadTexture(InputStream textureStream);
	protected abstract void bind();
	public abstract void destroy();

	protected abstract float getTextureWidth();
	protected abstract float getTextureHeight();

	public Font convertFont(Class<? extends Font> convertFont, String name) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return convertFont.getDeclaredConstructor(IntObject[].class, String.class).newInstance(this.charArray, name);
	}

	protected class IntObject
	{
		public short width;
		public short height;
		public short offsetX;
		public short offsetY;
		public float storedX;
		public float storedY;
		public float storedX1;
		public float storedY1;
	}

}

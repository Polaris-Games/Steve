package com.polaris.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;

public class WindowHelper 
{
	
	public static void createDefault(JFrame window)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int)(screen.width / 1.5), (int)(screen.height / 1.5));
		window.setBackground(Color.BLACK);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(true);
		//window.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		Display.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void setNoBorders()
	{
		
	}
	
	public static void setSize()
	{
		
	}

}

package com.polaris.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class WindowHelper 
{
	
	public static void createDefault(JFrame window)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize((int)(screen.width / 1.5), (int)(screen.height / 1.5));
		window.setBackground(Color.BLACK);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
	}
	
	public static void setNoBorders()
	{
		
	}
	
	public static void setSize()
	{
		
	}

}

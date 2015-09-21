package com.polaris.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;

public class Window extends JFrame
{
	
	private static final long serialVersionUID = 1L;

	public Window(String title, Canvas canvas)
	{
		super(title);
		getContentPane().add(canvas);
	}
	
	public void setDefault()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(screen.width / 1.5), (int)(screen.height / 1.5));
		setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		//window.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		Display.setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

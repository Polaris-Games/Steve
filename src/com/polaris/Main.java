package com.polaris;

import com.polaris.engine.*;
import com.polaris.steve.SteveApplication;

public class Main 
{
	public static void main(String[] args)
	{
		Application app = new Application(new SteveApplication());
		app.startApplication();
	}
}

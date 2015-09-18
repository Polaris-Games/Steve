package com.syllabus.engine;

public interface IProgressMonitor 
{
	
	public void update(double percentDone);
	public void update(String s);

}

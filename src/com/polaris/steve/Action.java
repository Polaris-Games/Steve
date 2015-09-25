package com.polaris.steve;


public class Action 
{	
	
	private boolean hasBeenCalled = false;
	
	/**
	 * The action to perform
	 * classes inheriting this should override
	 * @return false when finished
	 */
	public boolean call(Steve theBall)
	{
		hasBeenCalled = true;
		return true;
	}
	

	
	/**
	 * Resets all value to default
	 */
	public boolean reset()
	{

		hasBeenCalled = false;
		return true;
		
	}
	
	/**
	 * Checks whether call method has ever been called
	 * @return Whether call method has ever been called
	 */
	public final boolean isCalled()
	{
		return hasBeenCalled;
	}
	
}

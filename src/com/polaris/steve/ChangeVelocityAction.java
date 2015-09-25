package com.polaris.steve;

public class ChangeVelocityAction extends Action
{

	public double velocityX;
	public double velocityY;
	public ChangeVelocityAction(double velX, double velY)
	{
		this.velocityX = velX;
		this.velocityY = velY;
		
	}
	
	/**
	 * @return True for right now, just testing
	 */
	public boolean call(Steve theBall)
	{
		return true;	// for now
	}

}

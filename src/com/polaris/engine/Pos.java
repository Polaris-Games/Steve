package com.polaris.engine;

import java.util.ArrayList;
import java.util.List;

public class Pos 
{

	private double posX = 0;
	private double posY = 0;
	private double prevPosX = 0;
	private double prevPosY = 0;
	private double interpPosX = 0;
	private double interpPosY = 0;

	public Pos(double x, double y)
	{
		posX = prevPosX = interpPosX = x;
		posY = prevPosY = interpPosY = y;
	}
	
	public final void update(double delta)
	{
		interpPosX = Helper.getLinearValue(interpPosX, posX, Math.abs(posX - prevPosX), delta);
		interpPosY = Helper.getLinearValue(interpPosY, posY, Math.abs(posY - prevPosY), delta);
	}

	public double getX()
	{
		return interpPosX;
	}

	public double getY()
	{
		return interpPosY;
	}

	public double getRealX()
	{
		return posX;
	}

	public double getRealY()
	{
		return posY;
	}

	public void moveX(double x)
	{
		prevPosX = interpPosX = posX;
		posX += x;
	}

	public void moveY(double y)
	{
		prevPosY = interpPosY = posY;
		posY += y;
	}
	
	public void setPosition(double x, double y)
	{
		setX(x);
		setY(y);
	}

	public void setX(double x)
	{
		posX = prevPosX = interpPosX = x;
	}

	public void setY(double y)
	{
		posY = prevPosY = interpPosY = y;
	}

	public static class PosPhysics extends Pos
	{	
		private double velX = 0;
		private double velY = 0;
		private double mass = 0;
		private List<Force> allForces = new ArrayList<Force>();

		public PosPhysics(double x, double y, double m)
		{
			super(x, y);
			mass = m;
		}

		public PosPhysics(double x, double y, double vx, double vy, double m)
		{
			super(x, y);
			velX = vx;
			velY = vy;
			mass = m;
		}
		
		public double getVelocityX()
		{
			return velX;
		}

		public double getVelocityY()
		{
			return velY;
		}
		
		public void setVelocity(double x, double y)
		{
			setVelocityX(x);
			setVelocityY(y);
		}

		public void setVelocityX(double x)
		{
			velX = x;
		}

		public void setVelocityY(double y)
		{
			velY = y;
		}
		
		public void addForce(Force force)
		{
			int j = -1;
			for(int i = 0; i < allForces.size(); i++)
			{
				if(allForces.get(i).name.equalsIgnoreCase(force.name))
				{
					j = i;
					break;
				}
			}
			if(j == -1)
			{
				allForces.add(force);
			}
			else
			{
				allForces.get(j).setForce(force);
			}
		}
		
		public void removeForce(String s)
		{
			for(Force force : allForces)
			{
				if(force.name.equalsIgnoreCase(s))
				{
					allForces.remove(force);
					break;
				}
			}
		}
		
		public double getMass()
		{
			return mass;
		}

		public List<Force> getForces()
		{
			return allForces;
		}
	}

}

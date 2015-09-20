package com.polaris.engine;

public class Pos 
{
	
	private double posX = 0;
	private double posY = 0;
	private double prevPosX = 0;
	private double prevPosY = 0;
	
	public Pos(double x, double y)
	{
		posX = prevPosX = x;
		posY = prevPosY = posY;
	}
	
	public double getX()
	{
		return posX;
	}
	
	public double getY()
	{
		return posY;
	}
	
	public void setX(double x)
	{
		posX = x;
	}
	
	public void setY(double y)
	{
		posY = y;
	}
	
	public static class Pos2DPhysics extends Pos
	{	
		private double velX = 0;
		private double velY = 0;
		private double prevVelX = 0;
		private double prevVelY = 0;
		private double accelX = 0;
		private double accelY = 0;
		private double prevAccelX = 0;
		private double prevAccelY = 0;
		
		public Pos2DPhysics(double x, double y)
		{
			super(x, y);
		}
		
		public Pos2DPhysics(double x, double y, double vx, double vy)
		{
			super(x, y);
			velX = prevVelX = vx;
			velY = prevVelY = vy;
		}
		
		public Pos2DPhysics(double x, double y, double vx, double vy, double ax, double ay)
		{
			this(x, y);
			accelX = prevAccelX = ax;
			accelY = prevAccelY = ay;
		}
	}

	public static class Pos3D extends Pos
	{
		private double posZ = 0;
		private double prevPosZ = 0;
		
		public Pos3D(double x, double y, double z)
		{
			super(x, y);
			posZ = prevPosZ = z;
		}
	}
	
	public static class Pos3DPhysics extends Pos2DPhysics
	{
		private double posZ = 0;
		private double prevPosZ = 0;
		private double velZ = 0;
		private double prevVelZ = 0;
		private double accelZ = 0;
		private double prevAccelZ = 0;
		
		public Pos3DPhysics(double x, double y, double z)
		{
			super(x, y);
			posZ = prevPosZ = z;
		}
		
		public Pos3DPhysics(double x, double y, double z, double vx, double vy, double vz)
		{
			super(x, y, vx, vy);
			posZ = prevPosZ = z;
			velZ = prevVelZ = vz;
		}
		
		public Pos3DPhysics(double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az)
		{
			super(x, y, vx, vy, ax, ay);
			posZ = prevPosZ = z;
			velZ = prevVelZ = vz;
			accelZ = prevAccelZ = az;
		}
	}
	
}

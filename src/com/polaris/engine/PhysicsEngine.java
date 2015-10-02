package com.polaris.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.polaris.engine.Pos.PosPhysics;

public class PhysicsEngine 
{

	private double squareUnitToMeter = 0;
	private double generalGravity = 0;
	private double standardAirDensity = 0;
	private List<PhysicsEngine> colloborators = new ArrayList<PhysicsEngine>();
	private List<Entity> entityList = new ArrayList<Entity>();
	private List<Block> objectList = new ArrayList<Block>();

	public PhysicsEngine(String physicsResource) throws IOException
	{
		Properties propFile = new Properties();
		propFile.load(Helper.getResourceStream("physics/" + physicsResource + ".prop"));
		squareUnitToMeter = Double.valueOf(propFile.getProperty("squareUnitToMeter"));
		generalGravity = Double.valueOf(propFile.getProperty("gravity"));
		if(Boolean.valueOf(propFile.getProperty("useAirFriction")))
		{
			standardAirDensity = Double.valueOf(propFile.getProperty("standardAirDensity"));
		}
	}

	public void update(double delta)
	{
		for(Entity e : entityList)
		{
			PosPhysics position = e.getPosition();
			double accelX = 0;
			double accelY = 0;
			double newX = 0;
			double newY = 0;
			for(Force f : position.getForces())
			{
				accelX += f.xComponent;
				accelY += f.yComponent;
			}
			accelX /= position.getMass();
			accelY /= position.getMass();
			newX = position.getX() + position.getVelocityX() + .5 * accelX;
			newY = position.getY() + position.getVelocityY() + .5 * accelY;
			Shape shape = e.getEntityBounds().shift(newX, newY);
			for(Block b : objectList)
			{
				if(b.getBounds().inBounds(shape))
				{
					if(!e.onObjectCollide(b))
					{
						newX = b.getBounds().shiftX(e.getEntityBounds(), shape);
						newY = b.getBounds().shiftY(e.getEntityBounds(), shape);
					}
					b.onEntityCollide(e);
				}
			}
			position.setX(newX);
			position.setY(newY);
			position.setVelocityX(position.getVelocityX() + accelX);
			position.setVelocityY(position.getVelocityY() + accelY);
			e.update();
		}
		for(Entity e : entityList)
		{
			for(Entity e1 : entityList)
			{
				if(e.getEntityBounds().inBounds(e1.getEntityBounds()))
				{
					e.onEntityCollide(e1);
				}
			}
		}
	}

	public void addEntity(Entity entityToAdd)
	{
		entityList.add(entityToAdd);
		entityToAdd.getPosition().addForce(new Force("gravity", 0, generalGravity / squareUnitToMeter * entityToAdd.getPosition().getMass()));
	}

	public void addBlock(Block o)
	{
		objectList.add(o);
	}

	private List<Entity> getEntities()
	{
		return entityList;
	}

	private List<Block> getBlocks()
	{
		return objectList;
	}

	public void addPhysicsEngine(PhysicsEngine engine)
	{
		engine.addEngine(this);
		addEngine(engine);
	}

	public void removePhysicsEngine(PhysicsEngine engine, boolean addToThis)
	{
		if(addToThis)
		{
			for(Entity e : engine.getEntities())
			{
				addEntity(e);
			}
			for(Block o : engine.getBlocks())
			{
				addBlock(o);
			}
		}
		colloborators.remove(engine);
	}

	private void addEngine(PhysicsEngine engine)
	{
		colloborators.add(engine);
	}

}

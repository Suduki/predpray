package predpray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import static predpray.Constants.*;


public abstract class Animal {

	protected boolean walkThroughEdge;
	
	public enum Direction {
		EAST, WEST, NORTH, SOUTH, NONE
	}
	
	// Between [0,1]
	protected Double fertility;
	
	
	// 
	protected Double fertilityAge;
	protected Integer age;
	protected Integer sinceLastBaby;
	protected double hunger;
	protected Color color;
	
	protected abstract double getHungerLimitSearchForFood();
	protected abstract double getHungerLimitDeath();
	protected abstract double getHungerLimitFertile();
	protected abstract double getHungerConsumedWhenMating();
	protected abstract double getHungerAtBirth();

	public int positionX;
	public int positionY;
	
	public int id;

	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color.reset();
		this.color.append(color);
	}

	public Animal(Integer positionX, Integer positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.age = new Integer(0);
		this.sinceLastBaby = new Integer(0);
		this.fertility = new Double(1);
	}
	
	public boolean moveOneStepInCompletelyRandomDirection() {
		Integer randomDirection = RANDOM.nextInt(5);
		Direction dir = Direction.values()[randomDirection];
		return this.moveOneStep(dir);
	}
	
	public abstract boolean move();
	
	public boolean moveOneStep(Direction direction) {
		boolean moved = false;
		switch (direction) {
			case EAST:
				if (!Main.getMap().hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX + 1, positionY, walkThroughEdge))) {
					moved = false;
				} 
				else {
					positionX ++;
					positionX = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[0];
					moved = true;
				}
				break;
			case WEST:
				if (!Main.getMap().hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX - 1, positionY, walkThroughEdge))) {
					moved = false;
				} 
				else {
					positionX --;
					positionX = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[0];
					moved = true;
				}
				
				break;
			case NORTH:
				if (!Main.getMap().hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX, positionY + 1, walkThroughEdge))) {
					moved = false;
				} 
				else {
					positionY ++;
					positionY = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[1];
					moved = true;
				}
				
				break;
			case SOUTH:
				if (!Main.getMap().hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX, positionY - 1, walkThroughEdge))) {
					moved = false;
				} 
				else {
					positionY --;
					positionY = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[1];
					moved = true;
				}
				break;
			case NONE:
				moved = false;
				break;
			default:
				moved = false;
		}
		
		return moved;
	}
	public Direction oppositeDirection(Direction d) {
		if (d == Direction.EAST) {
			return Direction.WEST;
		}
		else if (d == Direction.WEST) {
			return Direction.EAST;
		}
		else if (d == Direction.NORTH) {
			return Direction.SOUTH;
		}
		else if (d == Direction.SOUTH) {
			return Direction.NORTH;
		}
		else {
			return Direction.NONE;
		}
	}
	public Direction sniffForFox() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		Scent[] scentRabbit = new Scent[4];
		Scent[] scentFox = new Scent[4];
		Direction[] direction = new Direction[4]; 

		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		
		Collections.shuffle(order, RANDOM);
		
		direction[order.get(0)] = Direction.EAST;
		scentRabbit[order.get(0)] = Main.getMap().getScentRabbitAt(Main.getMap().correctX(positionX + 1, walkThroughEdge), positionY);
		scentFox[order.get(0)] = Main.getMap().getScentFoxAt(Main.getMap().correctX(positionX + 1, walkThroughEdge), positionY);

		direction[order.get(1)] = Direction.WEST;
		scentRabbit[order.get(1)] = Main.getMap().getScentRabbitAt(Main.getMap().correctX(positionX - 1, walkThroughEdge), positionY);
		scentFox[order.get(1)] = Main.getMap().getScentFoxAt(Main.getMap().correctX(positionX - 1, walkThroughEdge), positionY);
		
		direction[order.get(2)] = Direction.NORTH;
		scentRabbit[order.get(2)] = Main.getMap().getScentRabbitAt(positionX, Main.getMap().correctY(positionY + 1, walkThroughEdge));
		scentFox[order.get(2)] = Main.getMap().getScentFoxAt(positionX, Main.getMap().correctY(positionY + 1, walkThroughEdge));
		
		direction[order.get(3)] = Direction.SOUTH;
		scentRabbit[order.get(3)] = Main.getMap().getScentRabbitAt(positionX, Main.getMap().correctY(positionY - 1, walkThroughEdge));
		scentFox[order.get(3)] = Main.getMap().getScentFoxAt(positionX, Main.getMap().correctY(positionY - 1, walkThroughEdge));
		
		int bestSmell = 0;
		Direction bestDirection = Direction.NONE;
		
		for (int i = 0; i < 4; i++)
		{
			if (scentFox[i].animalId != id && scentRabbit[i].animalId != id && bestSmell < scentFox[i].strength)
			{
				{
					bestSmell = scentFox[i].strength;
					bestDirection = direction[i];
				}
			}
		}
		
		return bestDirection;
	}
	/*public Direction sniffForFox() {
		
		// The search order
		ArrayList<Integer> order = new ArrayList<Integer>();
		
		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		
		Collections.shuffle(order, RANDOM);
		
		for (Integer i : order) {
			switch (i.intValue()) {
			case 0:
				if (map.nodeContainsFoxes(map.correctX(positionX + 1, walkThroughEdge), positionY)) {
					return Direction.EAST;
				}
				break;
			case 1:
				if (map.nodeContainsFoxes(map.correctX(positionX - 1, walkThroughEdge), positionY)) {
					return Direction.WEST;
				}
				break;
			case 2:
				if (map.nodeContainsFoxes(positionX, map.correctY(positionY + 1, walkThroughEdge))) {
					return Direction.NORTH;
				}
			case 3:
				if (map.nodeContainsFoxes(positionX, map.correctY(positionY - 1, walkThroughEdge))) {
					return Direction.SOUTH;
				}
			}
		}
		
		return Direction.NONE;
	}*/
	public Direction sniffForRabbit() {
		
		ArrayList<Integer> order = new ArrayList<Integer>();
		Scent[] scentRabbit = new Scent[4];
		Scent[] scentFox = new Scent[4];
		Direction[] direction = new Direction[4]; 

		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		
		Collections.shuffle(order, RANDOM);
		
		direction[order.get(0)] = Direction.EAST;
		scentRabbit[order.get(0)] = Main.getMap().getScentRabbitAt(Main.getMap().correctX(positionX + 1, walkThroughEdge), positionY);
		scentFox[order.get(0)] = Main.getMap().getScentFoxAt(Main.getMap().correctX(positionX + 1, walkThroughEdge), positionY);

		direction[order.get(1)] = Direction.WEST;
		scentRabbit[order.get(1)] = Main.getMap().getScentRabbitAt(Main.getMap().correctX(positionX - 1, walkThroughEdge), positionY);
		scentFox[order.get(1)] = Main.getMap().getScentFoxAt(Main.getMap().correctX(positionX - 1, walkThroughEdge), positionY);
		
		direction[order.get(2)] = Direction.NORTH;
		scentRabbit[order.get(2)] = Main.getMap().getScentRabbitAt(positionX, Main.getMap().correctY(positionY + 1, walkThroughEdge));
		scentFox[order.get(2)] = Main.getMap().getScentFoxAt(positionX, Main.getMap().correctY(positionY + 1, walkThroughEdge));
		
		direction[order.get(3)] = Direction.SOUTH;
		scentRabbit[order.get(3)] = Main.getMap().getScentRabbitAt(positionX, Main.getMap().correctY(positionY - 1, walkThroughEdge));
		scentFox[order.get(3)] = Main.getMap().getScentFoxAt(positionX, Main.getMap().correctY(positionY - 1, walkThroughEdge));
		
		int bestSmell = 0;
		Direction bestDirection = Direction.NONE;
		
		for (int i = 0; i < 4; i++)
		{
			if (scentRabbit[i].animalId != id && scentFox[i].animalId != id && bestSmell < scentRabbit[i].strength)
			{
				if (scentRabbit[i].strength > scentFox[i].strength) {
					bestSmell = scentRabbit[i].strength;
				}
				else {
					bestSmell = 1;
				}
				bestDirection = direction[i];
//				break; //TODO: investigate
			}
		}
		
		return bestDirection;
	}
	

	
//	public Direction sniffForRabbit() {
//		// The search order
//		ArrayList<Integer> order = new ArrayList<Integer>();
//		
//		order.add(new Integer(0));
//		order.add(new Integer(1));
//		order.add(new Integer(2));
//		order.add(new Integer(3));
//		
//		Collections.shuffle(order);
//		
//		for (Integer i : order) {
//			switch (i.intValue()) {
//			case 0:
//				if (map.nodeContainsRabbits(map.correctX(positionX + 1, walkThroughEdge), positionY)) {
//					return Direction.EAST;
//				}
//				break;
//			case 1:
//				if (map.nodeContainsRabbits(map.correctX(positionX - 1, walkThroughEdge), positionY)) {
//					return Direction.WEST;
//				}
//				break;
//			case 2:
//				if (map.nodeContainsRabbits(positionX, map.correctY(positionY + 1, walkThroughEdge))) {
//					return Direction.NORTH;
//				}
//			case 3:
//				if (map.nodeContainsRabbits(positionX, map.correctY(positionY - 1, walkThroughEdge))) {
//					return Direction.SOUTH;
//				}
//			}
//		}
//		
//		return Direction.NONE;
//	}

	public void die() {
		if (this instanceof Fox)
		{
			System.out.println("kill count = " + ((Fox) this).killCount);
		}
		AnimalHandler.killAnimal(this);
	}
	
	protected boolean isFertile() {
		if (age > fertilityAge && sinceLastBaby > fertilityAge 
				&& hunger < getHungerLimitFertile()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void age() {
		age ++;
		sinceLastBaby ++;
		hunger ++;
		if (hunger > getHungerLimitDeath()) {
			this.die();
		}
	}
	
	public <T extends Animal> void mateWith(T mother) {
		Animal father = this;

		// Spawn at father's position (same as mother's)
		int positionForChildX = father.positionX;
		int positionForChildY = father.positionY;

		if (father.isFertile() && mother.isFertile()) { //TODO Denna metod verkar sämst. Skriv om den till abstract!
			Constructor<? extends Animal> constructor;

			try {
				constructor = father.getClass().
						getConstructor(Integer.class, Integer.class);

				Animal child = constructor.newInstance(
						positionForChildX, positionForChildY);
				if (!AnimalHandler.addNewAnimal(child)) {
//					father.die();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	
	public void setHunger(Double hunger) {
		this.hunger = hunger;
	}

	public Double getHunger() {
		return hunger;
	}


}

package predpray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public abstract class Animal {

	private static Random random = new Random();
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
	
	protected abstract boolean isFertile();
	
	Map map;
	
	public int positionX;
	public int positionY;
	
	private float[] color;

	
	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public Animal(Map map, Integer positionX, Integer positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.map = map;
		this.age = new Integer(0);
		this.sinceLastBaby = new Integer(0);
		this.fertility = new Double(1);
	}
	
	public boolean moveOneStepInCompletelyRandomDirection() {
		Integer randomDirection = random.nextInt(5);
		Direction dir = Direction.values()[randomDirection];
		return this.moveOneStep(dir);
	}
	
	public abstract boolean move();
	
	public boolean moveOneStep(Direction direction) {
		switch (direction) {
			case EAST:
				if (!map.hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX + 1, positionY, walkThroughEdge))) {
					return false;
				} 
				else {
					positionX ++;
					positionX = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[0];
				}
				break;
			case WEST:
				if (!map.hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX - 1, positionY, walkThroughEdge))) {
					return false;
				} 
				else {
					positionX --;
					positionX = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[0];
				}
				
				break;
			case NORTH:
				if (!map.hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX, positionY + 1, walkThroughEdge))) {
					return false;
				} 
				else {
					positionY ++;
					positionY = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[1];
				}
				
				break;
			case SOUTH:
				if (!map.hasRoomForOneMoreAnimal(Map.correctCoordinates(positionX, positionY - 1, walkThroughEdge))) {
					return false;
				} 
				else {
					positionY --;
					positionY = Map.correctCoordinates(positionX, positionY, walkThroughEdge)[1];
				}
				break;
			case NONE:
				break;
			default:
				return false;
		}
		
		return true;
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
		
		// The search order
		ArrayList<Integer> order = new ArrayList<Integer>();
		
		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		
		Collections.shuffle(order);
		
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
	}
	public Direction sniffForRabbit() {
		// The search order
		ArrayList<Integer> order = new ArrayList<Integer>();
		
		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		
		Collections.shuffle(order);
		
		for (Integer i : order) {
			switch (i.intValue()) {
			case 0:
				if (map.nodeContainsRabbits(map.correctX(positionX + 1, walkThroughEdge), positionY)) {
					return Direction.EAST;
				}
				break;
			case 1:
				if (map.nodeContainsRabbits(map.correctX(positionX - 1, walkThroughEdge), positionY)) {
					return Direction.WEST;
				}
				break;
			case 2:
				if (map.nodeContainsRabbits(positionX, map.correctY(positionY + 1, walkThroughEdge))) {
					return Direction.NORTH;
				}
			case 3:
				if (map.nodeContainsRabbits(positionX, map.correctY(positionY - 1, walkThroughEdge))) {
					return Direction.SOUTH;
				}
			}
		}
		
		return Direction.NONE;
	}
	public void die() {
		AnimalHandler.killAnimal(this);
	}
	
//	protected boolean isFertile() {
//		if (age > fertilityAge && sinceLastBaby > fertilityAge) {
//			return true;
//		}
//		return false;
//	}
	
	public void age() {
		age ++;
		sinceLastBaby ++;
		if (isFertile()) {
			color[2] = 0.5f;
		}
		else {
			color[2] = 0;
		}
	}
	
	public <T extends Animal> void mateWith(T mother) {
		float red = new Random().nextFloat();
		float blue = new Random().nextFloat();
		float green = new Random().nextFloat();

		Animal father = this;

		// Let the child spawn at a random position
		//		int positionForChildX = new Random().nextInt(Map.numberOfNodesX);
		//		int positionForChildY = new Random().nextInt(Map.numberOfNodesY);

		// Spawn at father's position (same as mother's)
		int positionForChildX = father.positionX;
		int positionForChildY = father.positionY;

		if (father.isFertile() && mother.isFertile()) { //TODO Denna metod verkar sämst. Skriv om den till abstract!
			Constructor<? extends Animal> constructor;

			try {
				constructor = father.getClass().
						getConstructor(Map.class, Integer.class, Integer.class);

				Animal child = constructor.newInstance(
						Main.getMap(), positionForChildX, positionForChildY);
				if (!AnimalHandler.addNewAnimal(child)) {
					father.die();
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


}

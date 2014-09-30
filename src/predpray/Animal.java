package predpray;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;


public abstract class Animal {

	private static Random random = new Random();
	
	public enum DIRECTION {
		EAST, WEST, NORTH, SOUTH, NONE
	}
	
	// Between [0,1]
	protected Double fertility;
	
	
	// 
	protected Double fertilityAge;
	protected Integer age;
	protected Integer sinceLastBaby;
	
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
		DIRECTION dir = DIRECTION.values()[randomDirection];
		return this.moveOneStep(dir);
	}
	
	public abstract boolean move();
	
	public boolean moveOneStep(DIRECTION direction) {
		switch (direction) {
			case EAST:
				positionX ++;
				positionX = Map.correct(positionX, positionY)[0];
				break;
			case WEST:
				positionX --;
				positionX = Map.correct(positionX, positionY)[0];
				break;
			case NORTH:
				positionY ++;
				positionY = Map.correct(positionX, positionY)[1];
				break;
			case SOUTH:
				positionY --;
				positionY = Map.correct(positionX, positionY)[1];
				break;
			case NONE:
				break;
			default:
				return false;
		}
		
		return true;
	}
	public DIRECTION oppositeDirection(DIRECTION d) {
		if (d == DIRECTION.EAST) {
			return DIRECTION.WEST;
		}
		else if (d == DIRECTION.WEST) {
			return DIRECTION.EAST;
		}
		else if (d == DIRECTION.NORTH) {
			return DIRECTION.SOUTH;
		}
		else if (d == DIRECTION.SOUTH) {
			return DIRECTION.NORTH;
		}
		else {
			System.out.println("Direction = NONE in Animal.oppositeDirection()!");
			return DIRECTION.NONE;
		}
	}
	public DIRECTION sniffForFox() {
		if (map.nodeContainsFoxes(map.correctX(positionX + 1), positionY)) {
			return DIRECTION.EAST;
		}
		if (map.nodeContainsFoxes(map.correctX(positionX - 1), positionY)) {
			return DIRECTION.WEST;
		}
		if (map.nodeContainsFoxes(positionX, map.correctY(positionY + 1))) {
			return DIRECTION.NORTH;
		}
		if (map.nodeContainsFoxes(positionX, map.correctY(positionY - 1))) {
			return DIRECTION.SOUTH;
		}
		return DIRECTION.NONE;
	}
	public DIRECTION sniffForRabbit() {
		if(map.nodeContainsRabbits(map.correctX(positionX + 1), positionY)) {
			return DIRECTION.EAST;
		}
		if(map.nodeContainsRabbits(map.correctX(positionX - 1), positionY)) {
			return DIRECTION.WEST;
		}
		if(map.nodeContainsRabbits(positionX, map.correctY(positionY + 1))) {
			return DIRECTION.NORTH;
		}
		if(map.nodeContainsRabbits(positionX, map.correctY(positionY - 1))) {
			return DIRECTION.SOUTH;
		}
		return DIRECTION.NONE;
	}
	public void die() {
		AnimalHandler.killAnimal(this);
	}
	
	protected boolean isFertile() {
		if (age > fertilityAge && sinceLastBaby > fertilityAge) {
			return true;
		}
		return false;
	}
	
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
		
		if (father.isFertile() && mother.isFertile()) { //TODO Denna metod verkar sämst. Skriv om den till abstract!
			Constructor<? extends Animal> constructor;
			try {
				constructor = father.getClass().
						getConstructor(Map.class, Integer.class, Integer.class);
				Animal child = constructor.newInstance(
						Main.getMap(), father.positionX, father.positionY);
				AnimalHandler.addAnimal(child);
			} catch (Exception e) {
				e.printStackTrace();
			}
			father.sinceLastBaby = 0;
			
		}
		
	}

	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}


}

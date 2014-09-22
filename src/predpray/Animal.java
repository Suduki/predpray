package predpray;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;


public abstract class Animal {

	public static final int DIRECTION_EAST = 0;
	public static final int DIRECTION_WEST = 1;
	public static final int DIRECTION_NORTH = 2;
	public static final int DIRECTION_SOUTH = 3;
	public static final int DIRECTION_NONE = 4;
	
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
		this.fertilityAge = new Double(10.5);
	}
	
	public boolean moveOneStepInCompletelyRandomDirection() {
		Integer randomDirection = new Random().nextInt(5);
		return this.moveOneStep(randomDirection);
	}
	
	public boolean moveOneStep(int direction) {
		switch (direction) {
			case DIRECTION_EAST:
				positionX ++;
				positionX = Map.correct(positionX, positionY)[0];
				break;
			case DIRECTION_WEST:
				positionX --;
				positionX = Map.correct(positionX, positionY)[0];
				break;
			case DIRECTION_NORTH:
				positionY ++;
				positionY = Map.correct(positionX, positionY)[1];
				break;
			case DIRECTION_SOUTH:
				positionY --;
				positionY = Map.correct(positionX, positionY)[1];
				break;
			case DIRECTION_NONE:
				break;
			default:
				return false;
		}
		
		return true;
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
			color[2] = 1;
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

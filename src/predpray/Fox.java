package predpray;


public class Fox extends Animal {

	private double hunger;
	
	private static double starvationLimitForDeath = 20d;
	public static double HUNGER_AT_CANNIBALISM = starvationLimitForDeath-3d;
	private double hungerConsumedWhenMating = 10d;
	
	private double hungerAtBirth = hungerConsumedWhenMating;
	
	private double dontMateDueToHunger = 0.5d; 

	
	public Fox(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.walkThroughEdge = true;
		this.setColor(new float[] {1,0,0});
		this.hunger = hungerAtBirth;
		this.fertilityAge = 2D;
		
	}
	
	public Fox(Fox mother, Fox father) {
		super(Main.getMap(), mother.getPositionX(), mother.getPositionY());
	}
	
	/**
	 * This is the same as using the constructor
	 * @param mother
	 * @param father
	 * @return
	 */
	public static Fox mate(Fox mother, Fox father) {
		return new Fox(mother, father);
	}
	
	
	public void eat(Animal animalToEat) {
		if (animalToEat instanceof Fox)
			hunger = hunger - (((Fox)animalToEat).starvationLimitForDeath - ((Fox)animalToEat).getHunger());
		if (animalToEat instanceof Rabbit)
			hunger = hunger - ((Rabbit)animalToEat).energy;
		if (hunger < 0) hunger = 0D;
//		hunger = 0d;
		
		animalToEat.die();
	}
	
	@Override
	public boolean isFertile() { 
		if (age > fertilityAge && sinceLastBaby > fertilityAge 
				&& hunger < starvationLimitForDeath * dontMateDueToHunger - 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
//	@Override
//	public <T extends Animal> void mateWith(T mother) {
////		if (hunger < starvationLimitForDeath * dontMateDueToHunger.intValue()) {
//			super.mateWith(mother);
//			this.hunger = this.hunger + hungerConsumedWhenMating;
//			((Fox) mother).setHunger(((Fox) mother).getHunger() + ((Fox) mother).hungerConsumedWhenMating);
////		}
//	}
	public boolean move() {
		
		Direction dirToFox = sniffForFox();
		
		if (dirToFox != Direction.NONE && isFertile()) {
			return moveOneStep(dirToFox);
		}
		
		Direction dirToRabbit = sniffForRabbit();
		if (dirToRabbit == Direction.NONE) {
			return moveOneStepInCompletelyRandomDirection();
		}
		
		return moveOneStep(dirToRabbit);
	}
	private Direction sniffForRabbitFromPosition(int x, int y) {
		if(map.nodeContainsRabbits(map.correctX(x + 1, walkThroughEdge), y)) {
			return Direction.EAST;
		}
		if(map.nodeContainsRabbits(map.correctX(x - 1, walkThroughEdge), y)) {
			return Direction.WEST;
		}
		if(map.nodeContainsRabbits(x, map.correctY(y + 1, walkThroughEdge))) {
			return Direction.NORTH;
		}
		if(map.nodeContainsRabbits(x, map.correctY(y - 1, walkThroughEdge))) {
			return Direction.SOUTH;
		}
		return Direction.NONE;
	
	}
	

	
	public void setHunger(Double hunger) {
		this.hunger = hunger;
	}

	public Double getHunger() {
		return hunger;
	}

	@Override
	public void age() {
		super.age();
		
		hunger ++;
		if (hunger > starvationLimitForDeath) {
			this.die();
		}
	}
	
}

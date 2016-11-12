package predpray;


public class Fox extends Animal {

	private double hunger;
	private static final double HUNGER_LIMIT_SEARCH_FOR_FOOD = 25d;
	private static final double HUNGER_LIMIT_DEATH = 50d;
	private static final double HUNGER_LIMIT_FERTILE = 10d;
	public static double HUNGER_LIMIT_CANNIBALISM = HUNGER_LIMIT_DEATH;
	
	
	private static final double HUNGER_CONSUMED_WHEN_MATING = 30d;
	
	private double hungerAtBirth = HUNGER_CONSUMED_WHEN_MATING;
	

	
	public Fox(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.walkThroughEdge = true;
		this.setColor(new float[] {1,0,0});
		this.hunger = hungerAtBirth;
		this.fertilityAge = 10D;
		
	}
	
	public Fox(Fox mother, Fox father) {
		super(main.getMap(), mother.getPositionX(), mother.getPositionY());
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
			hunger = hunger - (Fox.HUNGER_LIMIT_DEATH - ((Fox)animalToEat).getHunger());
		if (animalToEat instanceof Rabbit)
			hunger = hunger - ((Rabbit)animalToEat).energy;
		if (hunger < 0) hunger = 0D;
		animalToEat.die();
	}
	
	@Override
	public boolean isFertile() { 
		if (age > fertilityAge && sinceLastBaby > fertilityAge 
				&& hunger < HUNGER_LIMIT_FERTILE) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean move() {
		
		Direction dirToRabbit = sniffForRabbit();
		if (dirToRabbit != Direction.NONE && hunger > HUNGER_LIMIT_SEARCH_FOR_FOOD) {
			return moveOneStep(dirToRabbit);
		}
		
		Direction dirToFox = sniffForFox();
		if (dirToFox != Direction.NONE && isFertile()) {
			return moveOneStep(dirToFox);
		}
		
		return moveOneStepInCompletelyRandomDirection();
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
		if (hunger > HUNGER_LIMIT_DEATH) {
			this.die();
		}
	}
	
}

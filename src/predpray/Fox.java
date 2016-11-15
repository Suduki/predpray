package predpray;

import static predpray.Constants.*;

public class Fox extends Animal {

	private static final double HUNGER_LIMIT_SEARCH_FOR_FOOD = 25d;
	private double HUNGER_LIMIT_DEATH = 100d;
	private static final double HUNGER_LIMIT_FERTILE = 10d;
	private static final double HUNGER_CONSUMED_WHEN_MATING = 100d;
	private final double HUNGER_AT_BIRTH = HUNGER_LIMIT_DEATH - HUNGER_CONSUMED_WHEN_MATING;

	public int killCount;
	
	public Fox(Integer positionX, Integer positionY) {
		super(positionX, positionY);
		this.walkThroughEdge = true;
		this.color = new PredPrayColor(1f, 0f, 0f);
		this.killCount = 0;
		this.fertilityAge = 10D;
		this.renderMe = RENDER_FOXES;
	}
	
	public Fox(Fox mother, Fox father) {
		super(mother.getPositionX(), mother.getPositionY());
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
		{
			System.err.println("ERROR: eat(animalToEat): trying to eat fox");
		}
//			hunger = hunger - (Fox.HUNGER_LIMIT_DEATH - ((Fox)animalToEat).getHunger());
		if (animalToEat instanceof Rabbit)
			hunger = hunger - ((Rabbit)animalToEat).energy * killCount;
		if (hunger < 0) hunger = 0D;
		killCount ++;
		HUNGER_LIMIT_DEATH ++;
		animalToEat.die();
	}
	
	public boolean move() {
		
		Direction dirToFox = sniffForFox();
		if (dirToFox != Direction.NONE && isFertile()) {
			return moveOneStep(dirToFox);
		}
		
		Direction dirToRabbit = sniffForRabbit();
		if (dirToRabbit != Direction.NONE) {
			return moveOneStep(dirToRabbit);
		}
		
		return moveOneStepInCompletelyRandomDirection();
	}
	


	@Override
	protected double getHungerLimitDeath() {
		return HUNGER_LIMIT_DEATH;
	}

	@Override
	protected double getHungerLimitSearchForFood() {
		return HUNGER_LIMIT_SEARCH_FOR_FOOD;
	}

	@Override
	protected double getHungerLimitFertile() {
		return HUNGER_LIMIT_FERTILE;
	}

	@Override
	protected double getHungerConsumedWhenMating() {
		return HUNGER_CONSUMED_WHEN_MATING;
	}

	@Override
	protected double getHungerAtBirth() {
		return HUNGER_AT_BIRTH;
	}
}

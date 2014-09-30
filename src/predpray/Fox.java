package predpray;


public class Fox extends Animal {

	private int hunger;
	
	
	private int hungerConsumedWhenMating = 3;
	private int hungerAtBirth = 1;
	private int starvationLimitForDeath = 8;
	
	private Double dontMateDueToHunger = 0.8D; 
	
	public Fox(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.setColor(new float[] {1,0,0});
		this.hunger = hungerAtBirth;
		this.fertilityAge = 10D;
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
		hunger = 0;
		animalToEat.die();
	}
	
	@Override
	public boolean isFertile() {
		if (super.isFertile() && hunger < starvationLimitForDeath * dontMateDueToHunger) {
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
		DIRECTION dirToRabbit = sniffForRabbit();
		DIRECTION dirToFox = sniffForFox();
		
		if (dirToFox != DIRECTION.NONE && isFertile()) {
			return moveOneStep(dirToFox);	
		}
		if (dirToRabbit == DIRECTION.NONE) {
			return moveOneStepInCompletelyRandomDirection();
		}
		return moveOneStep(dirToRabbit);
	}
	private DIRECTION sniffForRabbitFromPosition(int x, int y) {
		if(map.nodeContainsRabbits(map.correctX(x + 1), y)) {
			return DIRECTION.EAST;
		}
		if(map.nodeContainsRabbits(map.correctX(x - 1), y)) {
			return DIRECTION.WEST;
		}
		if(map.nodeContainsRabbits(x, map.correctY(y + 1))) {
			return DIRECTION.NORTH;
		}
		if(map.nodeContainsRabbits(x, map.correctY(y - 1))) {
			return DIRECTION.SOUTH;
		}
		return DIRECTION.NONE;
	
	}
	

	
	private void setHunger(int hunger) {
		this.hunger = hunger;
	}

	private int getHunger() {
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

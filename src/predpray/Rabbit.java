package predpray;



public class Rabbit extends Animal {
	
	public double energy; 
	private static final double HUNGER_LIMIT_SEARCH_FOR_FOOD = 25d;
	private static final double HUNGER_LIMIT_DEATH = 50d;
	private static final double HUNGER_LIMIT_FERTILE = 10d;
	private static final double HUNGER_CONSUMED_WHEN_MATING = 10d;
	private static double HUNGER_AT_BIRTH = HUNGER_LIMIT_DEATH - HUNGER_CONSUMED_WHEN_MATING;
	
	public Rabbit(Integer positionX, Integer positionY) {
		super(positionX, positionY);
		this.walkThroughEdge = true;
		this.color = new Color(0,0,1);
		this.fertilityAge = 20D;
		this.energy = 50D;
	}

	@Override
	public boolean move() {
		Direction directionToFox;
		if ((directionToFox = sniffForFox()) != Direction.NONE)
		{
			// Fox nearby
			return moveOneStep(oppositeDirection(directionToFox));
		}
		else
		{
			Direction directionToRabbit;
			// No fox nearby!
			if (isFertile() && (directionToRabbit = sniffForRabbit()) != Direction.NONE)
			{
				// Smelling a fluffy friend, and I am fertile. Let's find the friend!
				return moveOneStep(directionToRabbit);
			}
			return moveOneStepInCompletelyRandomDirection();
		}
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

	public void eatGrass() {

		
//		public int positionX;
//		public int positionY;
	}
	
	
}

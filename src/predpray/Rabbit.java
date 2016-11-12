package predpray;



public class Rabbit extends Animal {
	
	public double energy; 
	private static final double HUNGER_LIMIT_SEARCH_FOR_FOOD = 40d;
	private static final double HUNGER_LIMIT_DEATH = 50d;
	private static final double HUNGER_LIMIT_FERTILE = 10d;
	private static final double HUNGER_CONSUMED_WHEN_MATING = 1d;
	private static double HUNGER_AT_BIRTH = HUNGER_LIMIT_DEATH - HUNGER_CONSUMED_WHEN_MATING;
	
	private static final double HARVEST_SKILL = 20d;
	private static final double DIGESTION_EFFECTIVENESS = 0.1d;
	
	public Rabbit(Integer positionX, Integer positionY) {
		super(positionX, positionY);
		this.walkThroughEdge = true;
		this.color = new Color(0,0,1);
		this.fertilityAge = 5D;
		this.energy = 30D;
	}
	
	private boolean isHungry()
	{
		return hunger < HUNGER_LIMIT_SEARCH_FOR_FOOD;
	}

	@Override
	public boolean move() {
		Direction directionToFox;
		boolean moved = false;
		if ((directionToFox = sniffForFox()) != Direction.NONE && isHungry())
		{
			// Fox nearby
			moved = moveOneStep(oppositeDirection(directionToFox));
		}
		else if (isFertile())
		{
			Direction directionToRabbit = sniffForRabbit();
			// No fox nearby!
			if (directionToRabbit != Direction.NONE)
			{
				// Smelling a fluffy friend, and I am fertile. Let's find the friend!
				moved = moveOneStep(directionToRabbit);
			}
		}
//		else if (!isHungry())
//		{
//			hunger -= Main.map.getNodeAt(positionX, positionY).harvest(HARVEST_SKILL) * DIGESTION_EFFECTIVENESS;
//			moved = moveOneStepInCompletelyRandomDirection();
//		}
		else
		{
			// Hungry, not fertile, no enemy
			hunger -= Main.map.getNodeAt(positionX, positionY).harvest(HARVEST_SKILL) * DIGESTION_EFFECTIVENESS;
			moved = moveOneStepInCompletelyRandomDirection();
		}
		
		return moved;
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

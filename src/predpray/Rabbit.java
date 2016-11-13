package predpray;

import static predpray.Constants.RANDOM;

import java.util.ArrayList;
import java.util.Collections;

import predpray.Animal.Direction;



public class Rabbit extends Animal {
	
	public double energy; 
	private static final double HUNGER_LIMIT_SEARCH_FOR_FOOD = 40d;
	private static final double HUNGER_LIMIT_DEATH = 50d;
	private static final double HUNGER_LIMIT_FERTILE = 10d;
	private static final double HUNGER_CONSUMED_WHEN_MATING = 1d;
	private static double HUNGER_AT_BIRTH = HUNGER_LIMIT_DEATH - HUNGER_CONSUMED_WHEN_MATING;
	
	private static final double HARVEST_SKILL = 2d;
	private static final double DIGESTION_EFFECTIVENESS = 1.5d;
	
	public Rabbit(Integer positionX, Integer positionY) {
		super(positionX, positionY);
		this.walkThroughEdge = true;
		this.color = new Color(0.7f,0.7f,0.7f);
		this.fertilityAge = 10D;
		this.energy = 10D;
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
			Direction directionToGrass = searchForGrass();
			moved = moveOneStep(directionToGrass);
			hunger -= Main.map.getNodeAt(positionX, positionY).harvest(HARVEST_SKILL) * DIGESTION_EFFECTIVENESS;
		}
		
		return moved;
	}

	
	public Direction searchForGrass() {
		
		ArrayList<Integer> order = new ArrayList<Integer>();
		Direction[] direction = new Direction[5]; 
		double[] grassHeights = new double[5];
		
		order.add(new Integer(0));
		order.add(new Integer(1));
		order.add(new Integer(2));
		order.add(new Integer(3));
		order.add(new Integer(4));
		
		Collections.shuffle(order, RANDOM);
		
		direction[order.get(0)] = Direction.EAST;
		direction[order.get(1)] = Direction.WEST;
		direction[order.get(2)] = Direction.NORTH;
		direction[order.get(3)] = Direction.SOUTH;
		direction[order.get(4)] = Direction.NONE;
		
		
		direction[order.get(0)] = Direction.EAST;
		grassHeights[order.get(0)] = Main.getMap().getGrassHeightAt(Main.getMap().correctX(positionX + 1, walkThroughEdge), positionY);

		direction[order.get(1)] = Direction.WEST;
		grassHeights[order.get(1)] = Main.getMap().getGrassHeightAt(Main.getMap().correctX(positionX - 1, walkThroughEdge), positionY);
		
		direction[order.get(2)] = Direction.NORTH;
		grassHeights[order.get(2)] = Main.getMap().getGrassHeightAt(positionX, Main.getMap().correctY(positionY + 1, walkThroughEdge));
		
		direction[order.get(3)] = Direction.SOUTH;
		grassHeights[order.get(3)] = Main.getMap().getGrassHeightAt(positionX, Main.getMap().correctY(positionY - 1, walkThroughEdge));
		

		double bestHeight = 0;
		Direction bestDirection = direction[0]; // Random direction
		
		for (int i = 0; i < 5; i++)
		{
			if (grassHeights[i] > bestHeight) {
				bestHeight = grassHeights[i];
				bestDirection = direction[i];
			}
		}
		
		return bestDirection;
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

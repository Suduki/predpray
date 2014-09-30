package predpray;

import predpray.Animal.DIRECTION;


public class Rabbit extends Animal {
	
	public Rabbit(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.setColor(new float[] {0,1,0});
		this.fertilityAge = 4D;
	}

	@Override
	public boolean move() {
		DIRECTION directionToRabbit = sniffForRabbit();
		DIRECTION directionToFox = sniffForFox();
		
		if(directionToFox == DIRECTION.NONE) {
			// No fox nearby
			if (directionToRabbit != DIRECTION.NONE && isFertile()) {
				// Found a friend, and I am fertile. Let's find the friend!
				return moveOneStep(directionToRabbit);
			}
			return moveOneStepInCompletelyRandomDirection();
		}
		else {
			// Fox nearby!
			//moveOneStep(oppositeDirection(directionToFox));
			return moveOneStep(oppositeDirection(directionToFox));
		}
		
	}
	
	
}

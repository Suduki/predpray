package predpray;



public class Rabbit extends Animal {
	
	public double energy; 
	
	public Rabbit(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.walkThroughEdge = true;
		this.setColor(new float[] {0,1,0});
		this.fertilityAge = 10D;
		this.energy = 20D;
	}

	@Override
	public boolean move() {
		Direction directionToRabbit = sniffForRabbit();
		Direction directionToFox = sniffForFox();
		
		if(directionToFox == Direction.NONE) {
			// No fox nearby
			if (directionToRabbit != Direction.NONE && isFertile()) {
				// Found a friend, and I am fertile. Let's find the friend!
				return moveOneStep(directionToRabbit);
			}
			// No animal nearby
			return moveOneStepInCompletelyRandomDirection();
		}
		else {
			// Fox nearby!
			return moveOneStep(oppositeDirection(directionToFox));
		}
		
	}

	@Override
	protected boolean isFertile() {
		this.energy = new Double(sinceLastBaby);
		if(age > fertilityAge && sinceLastBaby > fertilityAge)
			return true;
		return false;
	}
	
	
}

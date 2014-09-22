package predpray;


public class Rabbit extends Animal {
	
	public Rabbit(Map map, Integer positionX, Integer positionY) {
		super(map, positionX, positionY);
		this.setColor(new float[] {0,1,0});
		this.fertilityAge = 4D;
	}
	
	
}

package predpray;

import static predpray.Constants.*;

public class Grass {
	private double length;
	
	public Grass(int length)
	{
		this.length = length;
	}
	
	public void grow()
	{
		length += GRASS_GROWTH;
		if (length > GRASS_MAX_LIMIT)
		{
			length = GRASS_MAX_LIMIT;
		}
	}
	
	public double cut(double harvestSkill)
	{
		double oldLength = length;
		length -= harvestSkill;
		if (length < 0)
		{
			length = 0;
		}
		
		return oldLength - length;
	}

	public double getLength() {
		return length;
	}
}

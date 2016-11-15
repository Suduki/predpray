package predpray;

import static predpray.Constants.*;

public class Grass {
	private double height;
	
	public Grass(double d)
	{
		this.height = d;
	}
	
	public void grow()
	{
		height += GRASS_GROWTH;
		if (height > GRASS_MAX_LIMIT)
		{
			height = GRASS_MAX_LIMIT;
		}
	}
	
	public double cut(double harvestSkill)
	{
		double oldHeight = height;
		height -= harvestSkill;
		if (height < 0)
		{
			height = 0;
		}
		
		return oldHeight - height;
	}

	public double getHeight() {
		return height;
	}
}

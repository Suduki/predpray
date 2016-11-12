package predpray;

public class Scent {
	public int strength;
	public int animalId;
	
	public Scent(int strength, int ownerId) {
		this.strength = strength;
		this.animalId = ownerId;
	}
	
	public int getStrength()
	{
		return strength;
	}
	
	public void refresh(int pStrength, int pAnimalId)
	{
		strength = pStrength;
		animalId = pAnimalId;
	}
	
	public void decay()
	{
		if (strength > 0)
		{
			strength --;
		}
		else
		{
			animalId = -1;
		}
	}
}

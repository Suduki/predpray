package predpray;

import static predpray.Constants.*;

public class Grass {
	private int length;
	
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
	
	public int cut(int pCutLength)
	{
		int oldLength = length;
		length -= pCutLength;
		if (length < 0)
		{
			length = 0;
		}
		
		return oldLength - length;
	}

	public int getLength() {
		return length;
	}
}

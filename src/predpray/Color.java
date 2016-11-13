package predpray;

import static predpray.Constants.*;

public class Color {
	
	public float green, red, blue;
	
	public Color(float red, float green, float blue) {
		this.green = green;
		this.red = red;
		this.blue = blue;
	}
	
	public void append(Color pC)
	{
		this.green += pC.green;
		this.red   += pC.red;
		this.blue  += pC.blue;
	}
	public void paint(Grass pGrass)
	{
		this.green = new Float(pGrass.getHeight())/GRASS_MAX_LIMIT*0.4f;
		this.green += 0.2f;
		this.red = 0.2f;
		this.blue = 0f;
	}
	
	public void reset()
	{
		this.green = 0;
		this.red   = 0;
		this.blue  = 0;
	}
}

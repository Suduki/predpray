package predpray;

import static predpray.Constants.MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE;
import static predpray.Constants.SCENT_MAX_FOX;
import static predpray.Constants.SCENT_MAX_RABBIT;

import java.util.ArrayList;


public class Node {
	
	private int positionX;
	private int positionY;
	private Scent scentRabbit;
	private Scent scentFox;
	private Grass grass;
	public static float displayWidth = new Float(DisplayHelper.SCREEN_WIDTH) / Map.numberOfNodesX;
	public static float displayHeight = new Float(DisplayHelper.SCREEN_HEIGHT) / Map.numberOfNodesY;
	private Animal[] animalsInNode;
	private Color color;
	
//	public static final int MAXIMUM_NUMBER_OF_ANIMALS = 10;
	
	public Node(int x, int y) {
		positionX = x;
		positionY = y;
		grass = new Grass(0);
		scentRabbit = new Scent(0, 0);
		scentFox = new Scent(0, 0);
		color = new Color(0f, 0f, 0f);
		animalsInNode = new Animal[MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE];
	}
	
	public boolean addAnimalToNode(Animal animal) {
		for (int i = 0; i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE; i++) {
			if (animalsInNode[i] == null) {
				animalsInNode[i] = animal;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The animal moved away or died.
	 * @param animal
	 */
	public void removeAnimalFromNode(Animal animal) {
		for (int i = 0; i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE; i++) {
			if (animalsInNode[i] != null && animalsInNode[i].equals(animal)) {
				animalsInNode[i] = null;
			}
		}
	}
	
	public Animal[] getAnimalsInNode() {
		return animalsInNode;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void renderSmell() {
		float red = 0f;
		float green = 0f;
		float blue = 0f;
		if (scentFox.strength > 0f) 
		{
			red += new Float(scentFox.strength)/SCENT_MAX_FOX/2;
			blue += new Float(scentFox.strength)/SCENT_MAX_FOX/2;
		}
		if (scentRabbit.strength > 0f) 
		{
			green += new Float(scentRabbit.strength)/SCENT_MAX_RABBIT/2;
			blue += new Float(scentRabbit.strength)/SCENT_MAX_RABBIT/2;
		}
		
		float screenPositionX = positionX * displayWidth;
		float screenPositionY = positionY * displayHeight;
		
		Main.displayHelper.renderQuad(red, green, blue, 
				screenPositionX, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY + displayHeight, 
				screenPositionX, screenPositionY + displayHeight); 
	}
	
	public void render() {
		
		float red = new Float(positionX)/Map.numberOfNodesX  / 2;
		float green = new Float((1 - Math.sqrt(positionY * positionY + positionX * positionX)/Map.numberOfNodesY)/2);
		float blue = new Float(positionY)/Map.numberOfNodesY / 2;
		
		float screenPositionX = positionX * displayWidth;
		float screenPositionY = positionY * displayHeight;
		
		Main.displayHelper.renderQuad(red, green, blue, 
				screenPositionX, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY + displayHeight, 
				screenPositionX, screenPositionY + displayHeight); 
	}
	
	public void renderGrass() {
		
		float screenPositionX = positionX * displayWidth;
		float screenPositionY = positionY * displayHeight;
		
		Main.displayHelper.renderQuad(color, 
				screenPositionX, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY, 
				screenPositionX + displayWidth, screenPositionY + displayHeight, 
				screenPositionX, screenPositionY + displayHeight); 
	}
	
	public static float getDisplayWidth() {
		return displayWidth;
	}

	public static float getDisplayHeight() {
		return displayHeight;
	}

	public boolean containsRabbits() {
		int i = 0;
		while(i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE) {
			if (animalsInNode[i] != null && animalsInNode[i].getClass() == Rabbit.class) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	public ArrayList<Animal> getAllAnimalsOfType(Class<?> c) {
		int i = 0;
		ArrayList<Animal> animals = new ArrayList<Animal>();
		while(i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE) {
			if (animalsInNode[i] != null && animalsInNode[i].getClass() == c) {
				animals.add(animalsInNode[i]);
			}
			i++;
		}
		return animals;
	}

	public boolean containsFoxes() {
		int i = 0;
		while(i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE) {
			if (animalsInNode[i] != null && animalsInNode[i].getClass() == Fox.class) {
				return true;
			}
			i++;
		}
		return false;
	}

	public boolean hasRoomForMoreAnimals() {
		for (int i = 0; i < MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE; i++) {
			if (animalsInNode[i] == null) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		updateGrass();
		updateScent();
	}
	private void updateGrass() {
		if (!containsRabbits())
		{
			grass.grow();
		}
		color.paint(grass);
	}
	private void updateScent() {

		
		if (containsFoxes()) 
		{
			scentFox.refresh(SCENT_MAX_FOX, getAllAnimalsOfType(Fox.class).get(0).id);
		}
		else if (scentFox.strength > 0)
		{
			scentFox.decay();
		}
		
		
		if (containsRabbits()) 
		{
			scentRabbit.refresh(SCENT_MAX_RABBIT, getAllAnimalsOfType(Rabbit.class).get(0).id);
		}
		else if (scentRabbit.strength > 0)
		{
			scentRabbit.decay();
		}
	}

	public Scent getScentRabbit()
	{
		return scentRabbit;
	}
	public Scent getScentFox()
	{
		return scentFox;
	}

	public double harvest(double harvestSkill) {
		double magnitude = grass.cut(harvestSkill);
		color.paint(grass);
		return magnitude;
	}

}

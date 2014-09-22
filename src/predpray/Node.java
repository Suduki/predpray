package predpray;

import java.util.ArrayList;


public class Node {
	
	private static final int TOTAL_NUMBER_OF_ALLOWED_ANIMALS = AnimalHandler.TOTAL_NUMBER_OF_ALLOWED_ANIMALS;

	private int positionX;
	private int positionY;
	public static float displayWidth = DisplayHelper.SCREEN_WIDTH / Map.numberOfNodesX;
	public static float displayHeight = DisplayHelper.SCREEN_HEIGHT / Map.numberOfNodesY;
	private Animal[] animalsInNode;
	
	
//	public static final int MAXIMUM_NUMBER_OF_ANIMALS = 10;
	
	public Node(int x, int y) {
		positionX = x;
		positionY = y;
		animalsInNode = new Animal[TOTAL_NUMBER_OF_ALLOWED_ANIMALS];
	}
	
	public void addAnimalToNode(Animal animal) {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (animalsInNode[i] == null) {
				animalsInNode[i] = animal;
			}
		}
	}
	
	/**
	 * The animal moved away or died.
	 * @param animal
	 */
	public void removeAnimalFromNode(Animal animal) {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
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
//		if (!occupiedWith.isEmpty()) {
//			renderAnimalsInNode();
//		}
	}
//	public void renderAnimalsInNode() {

//	}
	
	public static float getDisplayWidth() {
		return displayWidth;
	}

	public static float getDisplayHeight() {
		return displayHeight;
	}

}

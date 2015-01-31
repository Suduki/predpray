package predpray;



public class Node {
	
	public static final int MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE = 5;
	
	private int positionX;
	private int positionY;
	public static float displayWidth = DisplayHelper.SCREEN_WIDTH / Map.numberOfNodesX;
	public static float displayHeight = DisplayHelper.SCREEN_HEIGHT / Map.numberOfNodesY;
	private Animal[] animalsInNode;
	
	
//	public static final int MAXIMUM_NUMBER_OF_ANIMALS = 10;
	
	public Node(int x, int y) {
		positionX = x;
		positionY = y;
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

}

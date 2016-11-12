package predpray;

import static predpray.Constants.*;

public class Map {

	public static final int numberOfNodesX = 48*MAP_MULTIPLIER; //TODO öka storlek, kolla svart ruta.
	public static final int numberOfNodesY = 48*MAP_MULTIPLIER;
	private Node[][] nodes;
	
	public Map() {
		nodes = new Node[numberOfNodesX][numberOfNodesY];
		for (int i = 0; i < numberOfNodesX; i++) {
			for (int j = 0; j < numberOfNodesY; j++) {
				nodes[i][j] = new Node(i, j);
			}
		}
	}
	
	public void renderMap() {
		for (int i = 0; i < numberOfNodesX; i++) {
			for (int j = 0; j < numberOfNodesY; j++) {
				nodes[i][j].render();
			}
		}
	}
	
	public void renderSmell() {
		for (int i = 0; i < numberOfNodesX; i++) {
			for (int j = 0; j < numberOfNodesY; j++) {
				nodes[i][j].renderSmell();
			}
		}
	}

	public Node getNodeAt(int positionX, int positionY) {

		return nodes[positionX][positionY];
	}

	/**
	 * Corrects an index to guarantee inside bounds.
	 * @param positionX
	 * @param positionY
	 * @return
	 */
	public static int[] correctCoordinates(int positionX, int positionY, boolean walkThroughEdge) {
		if(walkThroughEdge) {
			if (positionX == numberOfNodesX) {
				positionX = 0;
			}
			if (positionX == -1) {
				positionX = numberOfNodesX - 1;
			}
			if (positionY == numberOfNodesY) {
				positionY = 0;
			}
			if (positionY == -1) {
				positionY = numberOfNodesY - 1;
			}
		}
		else {
			if (positionX == numberOfNodesX) {
				positionX --;
			}
			if (positionX == -1) {
				positionX = 0;
			}
			if (positionY == numberOfNodesY) {
				positionY --;
			}
			if (positionY == -1) {
				positionY = 0;
			}
		}
		return new int[] {positionX, positionY};
	}
	
	public void removeAnimalFromNode(Animal animal) {
		nodes[animal.getPositionX()][animal.getPositionY()].removeAnimalFromNode(animal);
	}

	public boolean addAnimalToNode(Animal animal) {
		return nodes[animal.getPositionX()][animal.getPositionY()].addAnimalToNode(animal);
	}

	public Animal[] getAnimalsAtSameNodeAsOtherAnimal(Animal animal) {
		return nodes[animal.getPositionX()][animal.getPositionY()].getAnimalsInNode();
	}

	public Animal[] getAnimalsAtNode(int positionX, int positionY) {
		return nodes[positionX][positionY].getAnimalsInNode();
	}
	public Animal[] getAnimalsAtNode(Node n) {
		return n.getAnimalsInNode();
	}
	public boolean nodeContainsRabbits(int positionX, int positionY) {
		return nodes[positionX][positionY].containsRabbits();
	}
	public boolean nodeContainsFoxes(int positionX, int positionY) {
		return nodes[positionX][positionY].containsFoxes();
	}
	public int correctX(int positionX, boolean walkThroughEdge) {
		if (walkThroughEdge) {
			if (positionX == numberOfNodesX) {return 0;} 
			else if (positionX == -1) {return numberOfNodesX-1;}
			else {return positionX;}
		} 
		else {
			if (positionX == numberOfNodesX) {return numberOfNodesX-1; }
			else if (positionX == -1) {return 0;}
			else {return positionX;}
		}	
	}
	public int correctY(int positionY, boolean walkThroughEdge) {
		if (walkThroughEdge) {			
			if (positionY == numberOfNodesY){ return 0; }
			else if (positionY == -1) {return numberOfNodesY-1;}
			else {return positionY;}
		}
		else {
			if (positionY == numberOfNodesY) {return numberOfNodesY-1;} 
			else if (positionY == -1) {return 0;}
			else {return positionY;}
		}
	}
//	public ArrayList<Node> findNodesContainingInteractingAnimals() {
//		ArrayList<Node> interactingAnimals = new ArrayList<>();
//		for (int i = 0; i < numberOfNodesX; i++) {
//			for (int j = 0; j < numberOfNodesY; j++) {
//				if (mapElements[i][j].getOccupiedWith().size() > 1) {
//					interactingAnimals.add(mapElements[i][j]);
//				}
//			}
//		}
//		return interactingAnimals;
//	}

	public boolean hasRoomForOneMoreAnimal(int[] coords) {
		if (nodes[coords[0]][coords[1]].hasRoomForMoreAnimals()) return true;
		return false;
	}

	public void updateAllNodes() {
		for (int i = 0; i < numberOfNodesX; ++i) 
		{
			for (int j = 0; j < numberOfNodesY; ++j) 
			{
				nodes[i][j].update();
			}
		}
	}

	public Scent getScentFoxAt(int pX, int pY) {
		return nodes[pX][pY].getScentFox();
	}
	public Scent getScentRabbitAt(int pX, int pY) {
		return nodes[pX][pY].getScentRabbit();
	}

}

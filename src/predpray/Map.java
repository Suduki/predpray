package predpray;

import java.util.ArrayList;


public class Map {

	public static final int numberOfNodesX = 10; //TODO öka storlek, kolla svart ruta.
	public static final int numberOfNodesY = 10;
	private Node[][] mapElements;
	// TODO implementera maxantal på en ruta
	
	public Map() {
		mapElements = new Node[numberOfNodesX][numberOfNodesY];
		for (int i = 0; i < numberOfNodesX; i++) {
			for (int j = 0; j < numberOfNodesY; j++) {
				mapElements[i][j] = new Node(i, j);
			}
		}
	}
	
	public void renderMap() {
		for (int i = 0; i < numberOfNodesX; i++) {
			for (int j = 0; j < numberOfNodesY; j++) {
				mapElements[i][j].render();
			}
		}
	}

	public Node getNodeAt(int positionX, int positionY) {

		return mapElements[positionX][positionY];
	}

	/**
	 * Corrects an index to guarantee inside bounds.
	 * @param positionX
	 * @param positionY
	 * @return
	 */
	public static int[] correct(int positionX, int positionY) {
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
			positionY = numberOfNodesY-1;
		}
		return new int[] {positionX, positionY};
	}

	public void removeAnimalFromNode(Animal animal) {
		mapElements[animal.getPositionX()][animal.getPositionY()].removeAnimalFromNode(animal);
	}

	public void addAnimalToNode(Animal animal) {
		mapElements[animal.getPositionX()][animal.getPositionY()].addAnimalToNode(animal);
	}

	public Animal[] getAnimalsAtSameNodeAsOtherAnimal(Animal animal) {
		return mapElements[animal.getPositionX()][animal.getPositionY()].getAnimalsInNode();
	}

	public Animal[] getAnimalsAtNode(int positionX, int positionY) {
		return mapElements[positionX][positionY].getAnimalsInNode();
	}
	public Animal[] getAnimalsAtNode(Node n) {
		return n.getAnimalsInNode();
	}
	public boolean nodeContainsRabbits(int positionX, int positionY) {
		return mapElements[positionX][positionY].containsRabbits();
	}
	public int correctX(int positionX) {
		if (positionX == numberOfNodesX) return 0; 
		else if (positionX == -1) return numberOfNodesX-1;
		else return positionX;
	}
	public int correctY(int positionY) {
		if (positionY == numberOfNodesY) return 0; 
		else if (positionY == -1) return numberOfNodesY-1;
		else return positionY;
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
}

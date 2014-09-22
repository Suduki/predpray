package predpray;

import java.util.ArrayList;


public class Map {

	public static final int numberOfNodesX = 30; //TODO öka storlek, kolla svart ruta.
	public static final int numberOfNodesY = 30;
	private Node[][] mapElements;
	
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

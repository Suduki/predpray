package predpray;

import static predpray.Constants.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.lwjgl.input.Keyboard;

public class Main {



	public static DisplayHelper displayHelper;
	public static Map map;

	public static void main(String[] args) throws IOException {

		displayHelper = new DisplayHelper();
		map = new Map();
		
		for (int i = 0; i < INIT_NUMBER_OF_RABBITS; i++) {
			createRandomRabbit();
		}
		for (int i = 0; i < INIT_NUMBER_OF_FOXES; i++) {
			createRandomFox();
		}



		//		while(!Display.isCloseRequested()) {
		//			myDisplayHelper.renderInit();
		//			
		//			
		//			Display.update();
		//			Display.sync(60);
		//		}

		//TODO egentligen borde INTE isCloseRequested vara i simulering och inte i render-tråden.
		// Rendertråden ansvarar för att lyssna på tangentbord och mus TODO fix
		//		checkKeyboardForDisplayChanges();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int iteration = 0;
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("filename.txt"), "utf-8"));
		try {
			while (displayHelper.renderThreadThread.isAlive()) {
				AnimalHandler.moveAllAnimalsOneStepAndInteract();
				map.updateAllNodes();
				//			ArrayList<Node> nodesWithInteractingAnimals = map.findNodesContainingInteractingAnimals();
				//			
				//			for (Node nodeWithInteractingAnimals : nodesWithInteractingAnimals) {
				//				AnimalHandler.interact(nodeWithInteractingAnimals.getOccupiedWith());
				//				
				//			}
				iteration ++;
				if(iteration % 1 == 0) {
					int numberOfAnimals = AnimalHandler.getNumberOfAnimals();
					int numberOfFoxes = AnimalHandler.getNumberOfFoxes();
					int numberOfRabbits = AnimalHandler.getNumberOfRabbits();
					writer.write(numberOfRabbits + " " + numberOfFoxes + "\n");
					System.out.println("numberOfAnimals = " + numberOfAnimals + 
							",    foxes = " + numberOfFoxes + ",   rabbits = " + numberOfRabbits);
					while (numberOfFoxes < MIN_NUMBER_OF_FOXES || numberOfRabbits > MIN_FOXES_PER_RABBITS * numberOfFoxes - 1) {
						System.out.print("Adding fox.");
						createRandomFox();
						numberOfFoxes++;
					}
					if (numberOfRabbits < MIN_NUMBER_OF_RABBITS)
						System.out.println("   Adding " + (MIN_NUMBER_OF_RABBITS - numberOfRabbits) + "Rabz.");
					while (numberOfRabbits < MIN_NUMBER_OF_RABBITS) {
						createRandomRabbit();
						numberOfRabbits++;
					}
				}

				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_1)) { 
					SLEEP_TIME = 10;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_2)) { 
					SLEEP_TIME = 100;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_3)) { 
					SLEEP_TIME = 200;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_4)) { 
					SLEEP_TIME = 500;
				}
				while (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
						continue;
					}
				}

			}
		}
		catch ( IllegalStateException e) {
			e.printStackTrace();
		}
		writer.close();

		displayHelper.exit();

	}


	private static void createRandomFox() {
		Fox fox = new Fox(RANDOM.nextInt(Map.numberOfNodesX), RANDOM.nextInt(Map.numberOfNodesY));
		AnimalHandler.addNewAnimal(fox);
	}
	private static void createRandomRabbit() {
		Rabbit rabbit = new Rabbit(RANDOM.nextInt(Map.numberOfNodesX), RANDOM.nextInt(Map.numberOfNodesY));
		AnimalHandler.addNewAnimal(rabbit);		
	}

	public static Map getMap() {
		return map;
	}
}

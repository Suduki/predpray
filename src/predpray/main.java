package predpray;

import static predpray.Constants.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Main {



	public static DisplayHelper displayHelper;
	public static Map map;

	public static void main(String[] args) throws Exception {

		displayHelper = new DisplayHelper();
		map = new Map();
		
//		for (int i = 0; i < INIT_NUMBER_OF_RABBITS; i++) {
//			createRandomRabbit();
//		}
//		for (int i = 0; i < INIT_NUMBER_OF_FOXES; i++) {
//			createRandomFox();
//		}
		
		createFoxesInASquare(0, 0, 10);
		createRabbitsInASquare(0, 0, 70);
		createFoxesInASquare(50, 50, 10);


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

		Thread.sleep(100);
		int iteration = 0;
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("filename.txt"), "utf-8"));
		try {
			Mouse.create();
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
					Thread.sleep(50);
   					if (Mouse.isButtonDown(0))
					{
//   						createFoxesInASquare();
   						System.out.println(Mouse.getX() + ", y " + Mouse.getY());
   						createFoxesInASquare(Mouse.getX()*Map.numberOfNodesX/DisplayHelper.SCREEN_WIDTH, 
   								Mouse.getY()*Map.numberOfNodesX/DisplayHelper.SCREEN_HEIGHT, 5);
   						Thread.sleep(1000);
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
	
	
	private static void createFoxesInASquare(int x, int y, int width) 
	{
		for (int i = x; i < x + width; ++i)
		{
			for (int j = y; j < y + width; ++j)
			{
				Fox fox = new Fox(i, j);
				AnimalHandler.addNewAnimal(fox);
			}	
		}
	}
	private static void createRabbitsInASquare(int x, int y, int width) 
	{
		for (int i = x; i < x + width; ++i)
		{
			for (int j = y; j < y + width; ++j)
			{
				Rabbit rabbit = new Rabbit(i, j);
				AnimalHandler.addNewAnimal(rabbit);
			}	
		}
	}

	public static Map getMap() {
		return map;
	}
}

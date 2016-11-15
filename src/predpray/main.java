package predpray;

import static predpray.Constants.*;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

public class Main {



	public static DisplayHelper displayHelper;
	public static Map map;

	public static void main(String[] args) throws Exception {

		displayHelper = new DisplayHelper();
		map = new Map();
		
		for (int i = 0; i < INIT_NUMBER_OF_RABBITS; i++) {
			createRandomRabbit();
		}
		for (int i = 0; i < INIT_NUMBER_OF_FOXES; i++) {
			createRandomFox();
		}
		
//		createFoxesInASquare(0, 0, 10);
//		createRabbitsInASquare(0, 0, 70);
//		createFoxesInASquare(50, 50, 10);


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
		
		Thread.sleep(1000);


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
//				if(iteration % 100 == 0) {
//					int numberOfAnimals = AnimalHandler.getNumberOfAnimals();
//					int numberOfFoxes = AnimalHandler.getNumberOfFoxes();
//					int numberOfRabbits = AnimalHandler.getNumberOfRabbits();
//					int bestFoxKillCount = AnimalHandler.getBestFox().killCount;
//					writer.write(numberOfRabbits + " " + numberOfFoxes + "\n");
//					while (numberOfFoxes < MIN_NUMBER_OF_FOXES || numberOfRabbits > MIN_FOXES_PER_RABBITS * (numberOfFoxes + 1)) {
//						System.out.print("Adding fox.");
//						createRandomFox();
//						numberOfFoxes++;
//					}
//					if (numberOfRabbits < MIN_NUMBER_OF_RABBITS)
//						System.out.println("   Adding " + (MIN_NUMBER_OF_RABBITS - numberOfRabbits) + "Rabz.");
//					while (numberOfRabbits < MIN_NUMBER_OF_RABBITS) {
//						createRandomRabbit();
//						numberOfRabbits++;
//					}
//				}

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
   						createFoxesInASquare(Mouse.getX()*NUM_NODES_X/DisplayHelper.SCREEN_WIDTH, 
   								Mouse.getY()*NUM_NODES_X/DisplayHelper.SCREEN_HEIGHT, 5);
   						Thread.sleep(1000);
					}
   					if (Mouse.isButtonDown(1))
					{
   						createRabbitsInASquare(Mouse.getX()*NUM_NODES_X/DisplayHelper.SCREEN_WIDTH, 
   								Mouse.getY()*NUM_NODES_X/DisplayHelper.SCREEN_HEIGHT, 5);
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
		Fox fox = new Fox(RANDOM.nextInt(NUM_NODES_X), RANDOM.nextInt(NUM_NODES_Y));
		AnimalHandler.addNewAnimal(fox);
	}
	private static void createRandomRabbit() {
		Rabbit rabbit = new Rabbit(RANDOM.nextInt(NUM_NODES_X), RANDOM.nextInt(NUM_NODES_Y));
		AnimalHandler.addNewAnimal(rabbit);		
	}
	
	
	public static void createFoxesInASquare(int x, int y, int width) 
	{
		for (int i = x; i < x + width; ++i)
		{
			for (int j = y; j < y + width; ++j)
			{
				AnimalHandler.addNewAnimal(new Fox(i, j));
			}	
		}
	}
	public static void createRabbitsInASquare(float f, float g, int width) 
	{
		for (int i = (int) f; i < f + width; ++i)
		{
			for (int j = (int) g; j < g + width; ++j)
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

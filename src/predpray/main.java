package predpray;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;


public class Main {

	public static final int INIT_NUMBER_OF_RABBITS = 2000;
	public static final int INIT_NUMBER_OF_FOXES = 300;
	private static final int MIN_NUMBER_OF_RABBITS = 1000;
	private static final int MIN_NUMBER_OF_FOXES = 30;
	
	public static DisplayHelper displayHelper;
	private static Map map;
	private static Random random = new Random();

	public static void main(String[] args) throws IOException {
		
		
		
		map = new Map();
		displayHelper = new DisplayHelper();
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
			/*
			 * let each animal firstly move, then interact
			 */
			AnimalHandler.moveAllAnimalsOneStepAndInteract();
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
				while (numberOfFoxes < MIN_NUMBER_OF_FOXES || numberOfRabbits > 90 * numberOfFoxes) {
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
			
//			animal.moveOneStepInCompletelyRandomDirection();
//			animal2.moveOneStepInCompletelyRandomDirection();
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			if (Keyboard.isKeyDown(Keyboard.KEY_0)) { 
				System.out.println("KEY 0 nedtryckt!");
				displayHelper.exit();
				System.exit(0);
			}
			while (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				try {
					Thread.sleep(10);
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
		Fox fox = new Fox(map, new Random().nextInt(Map.numberOfNodesX), new Random().nextInt(Map.numberOfNodesY));
		AnimalHandler.addNewAnimal(fox);
	}
	private static void createRandomRabbit() {
		Rabbit animal = new Rabbit(map, new Random().nextInt(Map.numberOfNodesX), new Random().nextInt(Map.numberOfNodesY));
		AnimalHandler.addNewAnimal(animal);		
	}

	public static Map getMap() {
		return map;
	}
}

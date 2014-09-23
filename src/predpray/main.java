package predpray;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;


public class Main {

	private static final int INIT_NUMBER_OF_RABBITS = 200;
	private static final int INIT_NUMBER_OF_FOXES = 15;
	
	public static DisplayHelper displayHelper;
	private static Map map;

	public static void main(String[] args) {
		
		
		
		map = new Map();
		displayHelper = new DisplayHelper();
		for (int i = 0; i < INIT_NUMBER_OF_RABBITS; i++) {
			Rabbit animal = new Rabbit(map, new Random().nextInt(Map.numberOfNodesX), new Random().nextInt(Map.numberOfNodesY));
			AnimalHandler.addAnimal(animal);
		}
		for (int i = 0; i < INIT_NUMBER_OF_FOXES; i++) {
			Fox fox = new Fox(map, new Random().nextInt(Map.numberOfNodesX), new Random().nextInt(Map.numberOfNodesY));
			AnimalHandler.addAnimal(fox);
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
		while (displayHelper.renderThreadThread.isAlive()) {
			/*
			 * let each animal firstly move, then interact
			 */
			AnimalHandler.moveAllAnimalsOneStepRandomDirectionAndInteract();
//			ArrayList<Node> nodesWithInteractingAnimals = map.findNodesContainingInteractingAnimals();
//			
//			for (Node nodeWithInteractingAnimals : nodesWithInteractingAnimals) {
//				AnimalHandler.interact(nodeWithInteractingAnimals.getOccupiedWith());
//				
//			}
			iteration ++;
			if(iteration % 4 == 0) {
				int numberOfAnimals = AnimalHandler.getNumberOfAnimals();
				System.out.println("numberOfAnimals = " + numberOfAnimals);
			}
			
//			animal.moveOneStepInCompletelyRandomDirection();
//			animal2.moveOneStepInCompletelyRandomDirection();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

		displayHelper.exit();
	}
	
	

	public static Map getMap() {
		return map;
	}
}

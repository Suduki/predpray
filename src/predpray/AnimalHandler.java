package predpray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;


public class AnimalHandler {
	public final static int TOTAL_NUMBER_OF_ALLOWED_ANIMALS = 
			Map.numberOfNodesX * Map.numberOfNodesY * Node.MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE/5; 
			

	private static int totalNumberOfLivingAnimals = 0;
	private static int totalNumberOfLivingRabbits = 0;
	private static int totalNumberOfLivingFoxes = 0;

	private static Animal[] allAnimals = new Animal[TOTAL_NUMBER_OF_ALLOWED_ANIMALS];

	public static boolean addNewAnimal(Animal animal) {

		// Add it to the first available spot in the list.
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] == null && main.getMap().addAnimalToNode(animal)) {
				animal.id = i;
				allAnimals[i] = animal;
				totalNumberOfLivingAnimals++;
				if (animal instanceof Rabbit) 
					totalNumberOfLivingRabbits ++;
				if (animal instanceof Fox) 
					totalNumberOfLivingFoxes ++;
				
				return true;
			}
		}

		return false;
	}

	public static void killAnimal(Animal animal) {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null && allAnimals[i].equals(animal)) {
				allAnimals[i] = null;
				totalNumberOfLivingAnimals--;
				if (animal instanceof Rabbit) 
					totalNumberOfLivingRabbits --;
				if (animal instanceof Fox) 
					totalNumberOfLivingFoxes --;
				main.getMap().removeAnimalFromNode(animal);
			}
		}
	}

	public static synchronized void renderAllAnimals() {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null) {
				if (allAnimals[i].getClass() == Rabbit.class) {
					DisplayHelper.render(allAnimals[i]);
				}
			}
		}
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null) {
				if (allAnimals[i].getClass() == Fox.class) {
					DisplayHelper.render(allAnimals[i]);
				}
			}
		}
	}

	public static void moveAllAnimalsOneStepRandomDirectionAndInteract() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			order.add(i);
		}
		Collections.shuffle(order);
		
		for (Integer i : order) {
			Animal animal = allAnimals[i];
			if (animal != null) {
				main.getMap().removeAnimalFromNode(animal);
				animal.moveOneStepInCompletelyRandomDirection();
				interact(main.getMap().getAnimalsAtSameNodeAsOtherAnimal(animal), animal);
				main.getMap().addAnimalToNode(animal);
				animal.age();
			}
		}
	}

	public static void moveAllAnimalsOneStepAndInteract() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			order.add(i);
		}
		Collections.shuffle(order);
		
		for (Integer i : order) {
			Animal animal = allAnimals[i];
			if (animal != null) {
				main.getMap().removeAnimalFromNode(animal);
				animal.move();
				main.getMap().addAnimalToNode(animal);
				interact(main.getMap().getAnimalsAtSameNodeAsOtherAnimal(animal), animal);					
				
				animal.age();
			}
		}
	}
	
	public static void interact(Animal[] sleepingAnimals, Animal walker) {
		
		int numberOfAnimalsInCell = sleepingAnimals.length;
		for (int i = 0; i < numberOfAnimalsInCell; i++) {
			Animal sleeper = sleepingAnimals[i];
			if (sleeper == null || sleeper == walker || sleeper.age == 0) {
				continue;
			}

			//TODO this should be a method in fox/rabbit
			if (walker instanceof Fox && sleeper instanceof Rabbit) {
				((Fox) walker).eat(sleeper);
			}
			else if (walker instanceof Rabbit && sleeper instanceof Fox) {
				((Fox) sleeper).eat(walker);
			}
			else if (walker instanceof Rabbit && sleeper instanceof Rabbit) {
				walker.mateWith(sleeper);
			}
			else if (walker instanceof Fox && sleeper instanceof Fox) {
				if (((Fox)walker).getHunger() > Fox.HUNGER_LIMIT_CANNIBALISM) {
					((Fox) walker).eat(sleeper);
				}
				else {
					walker.mateWith(sleeper);
				}
			}
		}
	}

	public static int getNumberOfAnimals() {
		return totalNumberOfLivingAnimals;
	}
	public static int getNumberOfRabbits() {
		return totalNumberOfLivingRabbits;
	}
	public static int getNumberOfFoxes() {
		return totalNumberOfLivingFoxes;
	}
	public static int getMean() {
		return 0;
		
	}
}

package predpray;

import static predpray.Constants.*;

import java.util.ArrayList;
import java.util.Collections;

public class AnimalHandler {

	private static int totalNumberOfLivingAnimals = 0;
	private static int totalNumberOfLivingRabbits = 0;
	private static int totalNumberOfLivingFoxes = 0;
	private static int killLimit = 0;

	private static Animal[] allAnimals = new Animal[TOTAL_NUMBER_OF_ALLOWED_ANIMALS];

	public static boolean addNewAnimal(Animal animal) {

		// Add it to the first available spot in the list.
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] == null && Main.getMap().addAnimalToNode(animal)) {
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

		if (animal.getClass().isInstance(Fox.class))
		{
			System.out.println("hej");
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
				Main.getMap().removeAnimalFromNode(animal);
			}
		}
	}

	public static synchronized void renderAllAnimals() {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null) {
				DisplayHelper.render(allAnimals[i]);
			}
		}
	}

	public static void moveAllAnimalsOneStepRandomDirectionAndInteract() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			order.add(i);
		}
		Collections.shuffle(order, RANDOM);
		
		for (Integer i : order) {
			Animal animal = allAnimals[i];
			if (animal != null) {
				Main.getMap().removeAnimalFromNode(animal);
				animal.moveOneStepInCompletelyRandomDirection();
				interact(Main.getMap().getAnimalsAtSameNodeAsOtherAnimal(animal), animal);
				Main.getMap().addAnimalToNode(animal);
				animal.age();
			}
		}
	}

	public static void moveAllAnimalsOneStepAndInteract() {
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			order.add(i);
		}
		Collections.shuffle(order, RANDOM);
		
		for (Integer i : order) {
			Animal animal = allAnimals[i];
			if (animal != null) {
				Main.getMap().removeAnimalFromNode(animal);
				animal.move();
				Main.getMap().addAnimalToNode(animal);
				interact(Main.getMap().getAnimalsAtSameNodeAsOtherAnimal(animal), animal);					
				
				animal.age();
			}
		}
	}
	
	public static void interact(Animal[] sleepingAnimals, Animal walker) {
		
		for (Animal sleeper : sleepingAnimals) {
			if (sleeper == null || sleeper == walker || sleeper.age == 0) {
				continue;
			}

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
				walker.mateWith(sleeper);
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

	public static Fox getBestFox() {
		int bestKill = 0;
		Fox bestKiller = null;
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null && allAnimals[i] instanceof Fox) {
				((Fox)allAnimals[i]).renderMe = RENDER_FOXES;
				if (((Fox)allAnimals[i]).killCount > bestKill)
				{
					bestKiller = (Fox)allAnimals[i];
					bestKill = bestKiller.killCount;
				}
			}
		}
		if (bestKiller != null)
		{
			bestKiller.renderMe = true;
		}
		return bestKiller;
	}

	public static ArrayList<Fox> getBestFoxes() {
		ArrayList<Fox> foxList = new ArrayList<Fox>();
		int numFoxesOverKillLimit;
		do {
			numFoxesOverKillLimit = 0;
			for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
				if (allAnimals[i] != null && allAnimals[i] instanceof Fox) {
					((Fox)allAnimals[i]).renderMe = RENDER_FOXES;
					if (((Fox)allAnimals[i]).killCount > killLimit)
					{
						numFoxesOverKillLimit++;
					}
				}
			}
		} while(numFoxesOverKillLimit > 10 && (killLimit ++) > 0);
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null && allAnimals[i] instanceof Fox) {
				if (((Fox)allAnimals[i]).killCount >= killLimit)
				{
					foxList.add((Fox)allAnimals[i]);
					((Fox)allAnimals[i]).renderMe = true;
				}
			}
		}
		return foxList;
	}
}

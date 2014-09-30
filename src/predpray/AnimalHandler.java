package predpray;

import java.util.ArrayList;
import java.util.Random;


public class AnimalHandler {
	public final static int TOTAL_NUMBER_OF_ALLOWED_ANIMALS = 1000;
	private static int totalNumberOfLivingAnimals = 0;
	private static int totalNumberOfLivingRabbits = 0;
	private static int totalNumberOfLivingFoxes = 0;

	private static Animal[] allAnimals = new Animal[TOTAL_NUMBER_OF_ALLOWED_ANIMALS];

	private static boolean startToKill;

	public static boolean addAnimal(Animal animal) {

		// Add it to the first available spot in the list.
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] == null) {
				allAnimals[i] = animal;
				totalNumberOfLivingAnimals++;
				Main.getMap().addAnimalToNode(animal);
				return true;
			}
		}

		// Add the animal to the Node
		return false;
	}

	public static void killAnimal(Animal animal) {
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			if (allAnimals[i] != null && allAnimals[i].equals(animal)) {
				allAnimals[i] = null;
				totalNumberOfLivingAnimals--;
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
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
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
		for (int i = 0; i < TOTAL_NUMBER_OF_ALLOWED_ANIMALS; i++) {
			Animal animal = allAnimals[i];
			if (animal != null) {
				Main.getMap().removeAnimalFromNode(animal);
				animal.move();
				interact(Main.getMap().getAnimalsAtSameNodeAsOtherAnimal(animal), animal);
				Main.getMap().addAnimalToNode(animal);
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
			boolean mate = new Random().nextBoolean();

			//			if (TOTAL_NUMBER_OF_ALLOWED_ANIMALS > 10) {
			//				startToKill = true;
			//			}
			//			if (startToKill) {
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
				walker.mateWith(sleeper);
			}
//			if (mate) {
//				try {
//					Animal.mate(sleeper, walker);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				sleeper.die();
//				continue;
//			}
			
			//			}
			//			else if (mate) {
			//			}
		}

		// Remove 
		//		interactingAnimals.clear();
		//		interactingAnimals.add(winner);
	}

	public static int getNumberOfAnimals() {
		// TODO Auto-generated method stub
		return totalNumberOfLivingAnimals;
	}
}

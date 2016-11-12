package predpray;

import java.util.Random;

public class Constants {
	public static final int MAP_MULTIPLIER = 3;
	public static final int INIT_NUMBER_OF_RABBITS = 100 * MAP_MULTIPLIER;
	public static final int INIT_NUMBER_OF_FOXES = 10 * MAP_MULTIPLIER;
	public static final int MIN_NUMBER_OF_RABBITS = 5 * MAP_MULTIPLIER * MAP_MULTIPLIER;
	public static final int MIN_NUMBER_OF_FOXES = 5 * MAP_MULTIPLIER * MAP_MULTIPLIER;
	
	public static int SLEEP_TIME = 10;
	
	public static final int SMELL_MAX_FOX = 20 ;
	public static final int SMELL_MAX_RABBIT = 10 ;
	
	public static final int GRASS_MAX_LIMIT = 5;
	public static final int GRASS_GROWTH = 1;
	
	public static Random RANDOM = new Random(1234);
	
	public static final int MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE = 4;
	public final static int TOTAL_NUMBER_OF_ALLOWED_ANIMALS = 
			Map.numberOfNodesX * Map.numberOfNodesY * MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE; 
}

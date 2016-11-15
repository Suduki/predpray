package predpray;

import java.util.Random;

public class Constants {
	public static final int MAP_MULTIPLIER = 6;
	public static final int INIT_NUMBER_OF_RABBITS = 100 * MAP_MULTIPLIER;
	public static final int INIT_NUMBER_OF_FOXES = 50 * MAP_MULTIPLIER;
	public static final int MIN_NUMBER_OF_RABBITS = 0 * MAP_MULTIPLIER * MAP_MULTIPLIER;
	public static final int MIN_NUMBER_OF_FOXES = 0 * MAP_MULTIPLIER * MAP_MULTIPLIER;
	public static final double MIN_FOXES_PER_RABBITS = 10000000000d;
	
	public static final boolean RENDER_FOXES = false;
	public static final boolean RENDER_RABBITS = false;
	
	public static int SLEEP_TIME = 10;
	
	public static final int SCENT_MAX_FOX = 50 ;
	public static final int SCENT_MAX_RABBIT = 5 ;
	
	public static final int GRASS_MAX_LIMIT = 20;
	public static final double GRASS_GROWTH = 0.04;
	
	public static Random RANDOM = new Random(1);
	
	public static final int MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE = 4;
	public final static int TOTAL_NUMBER_OF_ALLOWED_ANIMALS = 
			Map.numberOfNodesX * Map.numberOfNodesY * MAX_NUMBER_OF_ALLOWED_ANIMALS_ON_NODE; 
}

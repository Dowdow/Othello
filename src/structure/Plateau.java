package structure;

import java.util.HashMap;
import java.util.Map;

public class Plateau {
	public static final int DEFAULT_WIDTH = 8;
	public static final int DEFAULT_HEIGHT = 8;
	
	private Map<Position, Case> cases = new HashMap<Position, Case>();
	
	/**
	 * Constructor
	 */
	public Plateau() {
		initialisation(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}
	
	public Plateau(int height, int width) {
		if(height < 2 || width < 2) {
			throw new IllegalArgumentException("La taille minimale du tableau doit être de 2x2.");
		}
		initialisation(height, width);
	}
	
	private void initialisation(int height, int width) {
		
	}
	
	/**
	 * Ajoute une case au plateau.
	 * @param p
	 * @param c
	 */
	public void ajouterCase(Position p, Case c) {
		cases.put(p, c);
	}
	
	/**
	 * Supprime une case du plateau
	 * @param p
	 * @return
	 */
	public Case supprimerCase(Position p) {
		Case c = cases.get(p);
		cases.remove(p);
		return c;
	}	
}

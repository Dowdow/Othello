package structure;

import java.util.HashMap;
import java.util.Map;

public class Plateau {

    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_HEIGHT = 8;

    private Map<Position, Case> cases = new HashMap<Position, Case>();

    /**
     * Constructeur
     */
    public Plateau() {
        initialisation(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    /**
     * Constructeur complet
     * @param height
     * @param width 
     */
    public Plateau(int height, int width) {
        if (height < 2 || width < 2) {
            throw new IllegalArgumentException("La taille minimale du tableau doit être de 2x2.");
        }
        initialisation(height, width);
    }

    /**
     * Initialise le tableau
     * @param height
     * @param width 
     */
    private void initialisation(int height, int width) {
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                cases.put(new Position(i, j), new CaseVide());
            }
        }
    }

    /**
     * Ajoute ou modifie une case du plateau.
     *
     * @param p
     * @param c
     */
    public void setCase(Position p, Case c) {
        cases.put(p, c);
    }

    /**
     * Supprime une case du plateau
     *
     * @param p
     * @return
     */
    public Case supprimerCase(Position p) {
        Case c = cases.get(p);
        cases.remove(p);
        return c;
    }
}

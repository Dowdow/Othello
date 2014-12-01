package structure;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Plateau {

    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_HEIGHT = 8;

    private int height;
    private int width;

    private Map<Position, Case> cases = new TreeMap<Position, Case>();

    /**
     * Constructeur
     */
    public Plateau() {
        setHeight(DEFAULT_HEIGHT);
        setWidth(DEFAULT_WIDTH);
        initialisation(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    /**
     * Constructeur complet
     *
     * @param height
     * @param width
     */
    public Plateau(int height, int width) {
        if (height < DEFAULT_HEIGHT || width < DEFAULT_WIDTH) {
            throw new IllegalArgumentException("La taille minimale du tableau doit être de " + DEFAULT_HEIGHT + "x" + DEFAULT_WIDTH + ".");
        }
        setHeight(height);
        setWidth(width);
        initialisation(height, width);
    }

    /**
     * Initialise le tableau
     *
     * @param height
     * @param width
     */
    public void initialisation(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cases.put(new Position(i, j), new CaseDisponible());
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

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public Map<Position, Case> getCases() {
        return cases;
    }
}

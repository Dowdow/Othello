package structure;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Plateau {

    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_HEIGHT = 8;

    private int height;
    private int width;

    private Map<Position, Case> cases = new TreeMap<>();

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
        cases.clear();
        setHeight(height);
        setWidth(width);
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                if (i == height / 2 && j == width / 2) {
                    cases.put(new Position(i, j), new CaseBlanche());
                } else if (i == height / 2 && j == (width / 2) + 1) {
                    cases.put(new Position(i, j), new CaseNoire());
                } else if (i == (height / 2) + 1 && j == width / 2) {
                    cases.put(new Position(i, j), new CaseNoire());
                } else if (i == (height / 2) + 1 && j == (width / 2) + 1) {
                    cases.put(new Position(i, j), new CaseBlanche());
                } else {
                    cases.put(new Position(i, j), new CaseVide());
                }
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
     * Ajoute une case et calcule les cases a capturer
     *
     * @param p
     * @param c
     */
    public void capture(Position p, Case c) {
        Map<Position, Case> map;
        Case opponent;
        if (c instanceof CaseBlanche) {
            opponent = new CaseNoire();
        } else {
            opponent = new CaseBlanche();
        }
        cases.put(p, c);
        clearAvailables();
        String ligne = "", colonne = "";
        for (int i = 1; i <= width; i++) {
            ligne += cases.get(new Position(p.getX(), i));
        }
        map = captureLeftToRight(p.getY(), ligne, opponent, p, c, true);
        if (map != null) {
            cases.putAll(map);
        }
        map = captureRightToLeft(p.getY() - 2, ligne, opponent, p, c, true);
        if (map != null) {
            cases.putAll(map);
        }
        for (int i = 1; i <= height; i++) {
            colonne += cases.get(new Position(i, p.getY()));
        }
        map = captureLeftToRight(p.getX(), colonne, opponent, p, c, false);
        if (map != null) {
            cases.putAll(map);
        }
        map = captureRightToLeft(p.getX() - 2, colonne, opponent, p, c, false);
        if (map != null) {
            cases.putAll(map);
        }
    }

    private Map<Position, Case> captureLeftToRight(int i, String analyse, Case opponent, Position p, Case c, boolean ligne) {
        if (i >= analyse.length()) {
            return null;
        } else {
            Map<Position, Case> map;
            if (analyse.charAt(i) == c.toString().charAt(0)) {
                map = new HashMap<>();
                return map;
            } else {
                map = captureLeftToRight(i + 1, analyse, opponent, p, c, ligne);
                if (analyse.charAt(i) == opponent.toString().charAt(0) && map != null) {
                    if (ligne) {
                        map.put(new Position(p.getX(), i + 1), c);
                    } else {
                        map.put(new Position(i + 1, p.getY()), c);
                    }
                }
            }
            return map;
        }
    }

    private Map<Position, Case> captureRightToLeft(int i, String analyse, Case opponent, Position p, Case c, boolean ligne) {
        if (i < 0) {
            return null;
        } else {
            Map<Position, Case> map;
            if (analyse.charAt(i) == c.toString().charAt(0)) {
                map = new HashMap<>();
            } else {
                map = captureRightToLeft(i - 1, analyse, opponent, p, c, ligne);
                if (analyse.charAt(i) == opponent.toString().charAt(0) && map != null) {
                    if (ligne) {
                        map.put(new Position(p.getX(), i + 1), c);
                    } else {
                        map.put(new Position(i + 1, p.getY()), c);
                    }
                }
            }
            return map;
        }
    }

    /**
     * Remplace les cases disponibles par des cases vides
     */
    private void clearAvailables() {
        for (Map.Entry<Position, Case> entrySet : cases.entrySet()) {
            if (entrySet.getValue() instanceof CaseDisponible) {
                cases.put(entrySet.getKey(), new CaseVide());
            }
        }
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

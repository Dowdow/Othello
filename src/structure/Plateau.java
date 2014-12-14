package structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import player.Player;

public class Plateau {

    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_HEIGHT = 8;

    /**
     * Hauteur du plateau
     */
    private int height;
    /**
     * Largeur du plateau
     */
    private int width;
    /**
     * Cases du plateau
     */
    private Map<Position, Case> cases = new TreeMap<>();

    /**
     * Constructeur minimal
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
        setHeight(height);
        setWidth(width);
        initialisation(height, width);
    }

    /**
     * Constructeur par clonage
     *
     * @param p
     */
    public Plateau(Plateau p) {
        setHeight(p.getHeight());
        setWidth(p.getWidth());
        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            cases.put(new Position(entrySet.getKey().getX(), entrySet.getKey().getY()), entrySet.getValue());
        }
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
     * Calcule le score d'un joueur
     *
     * @param p
     * @return
     */
    public int calculScore(Player p) {
        int score = 0;
        for (Map.Entry<Position, Case> entrySet : cases.entrySet()) {
            if (entrySet.getValue().getClass() == p.getC().getClass()) {
                score++;
            }
        }
        return score;
    }

    /**
     * Transmorme les CaseVide en CaseDisponible dans le cas ou celle ci
     * permette un coup
     *
     * @param player
     * @return
     */
    public int casesAvailable(Player player) {
        List<Position> positions = new ArrayList<>();
        char me = player.getC().toString().charAt(0);
        char opponent;
        if (player.getC() instanceof CaseBlanche) {
            opponent = new CaseNoire().toString().charAt(0);
        } else {
            opponent = new CaseBlanche().toString().charAt(0);
        }
        //Parcours de toutes les lignes
        for (int i = 1; i <= height; i++) {
            String ligne = "";
            for (int j = 1; j <= width; j++) {
                ligne += cases.get(new Position(i, j)).toString();
            }
            positions.addAll(match(ligne, new Position(i, 1), me, opponent, 0, 1));
        }
        //Parcours de toutes les colonnes
        for (int i = 1; i <= width; i++) {
            String colonne = "";
            for (int j = 1; j <= height; j++) {
                colonne += cases.get(new Position(j, i)).toString();
            }
            positions.addAll(match(colonne, new Position(1, i), me, opponent, 1, 0));
        }
        //Parcours de toutes les diagonales gauche -> droite
        String diagonale;
        int i = 1, j = width - 2, x, y;
        while (i < height - 1) {
            diagonale = "";
            for (int k = i, l = j; k <= height && l <= width; k++, l++) {
                diagonale += cases.get(new Position(k, l));
            }
            positions.addAll(match(diagonale, new Position(i, j), me, opponent, 1, 1));
            if (j > 1) {
                j--;
            } else if (j == 1) {
                i++;
            }
        }
        //Parcours de toutes les diagonales droite -> gauche
        i = 1;
        j = 3;
        while (i < height - 1) {
            diagonale = "";
            for (int k = i, l = j; k <= height && l > 0; k++, l--) {
                diagonale += cases.get(new Position(k, l));
            }
            positions.addAll(match(diagonale, new Position(i, j), me, opponent, 1, -1));
            if (j < width) {
                j++;
            } else if (j == width) {
                i++;
            }
        }

        int nbDispo = 0;
        for (Iterator<Position> iterator = positions.iterator(); iterator.hasNext();) {
            cases.put(iterator.next(), new CaseDisponible());
            nbDispo++;
        }
        return nbDispo;
    }

    /**
     * Recherche un coup disponible
     *
     * @return
     */
    private List<Position> match(String analyse, Position p, char me, char opponent, int x, int y) {
        List<Position> liste = new ArrayList<>();
        Position potential = null;
        boolean match = false;
        int px = p.getX(), py = p.getY();
        for (int i = 0; i < analyse.length() - 1; i++) {
            if (analyse.charAt(i) == 'V') {
                if (i + 1 < analyse.length() - 1 && analyse.charAt(i + 1) == opponent) {
                    potential = new Position(px, py);
                    match = true;
                } else {
                    match = false;
                }
            } else if (analyse.charAt(i) == me) {
                if (match) {
                    match = false;
                    liste.add(potential);
                }
            }
            px += x;
            py += y;
        }
        for (int i = analyse.length() - 1; i > 0; i--) {
            if (analyse.charAt(i) == 'V') {
                if (i - 1 > 0 && analyse.charAt(i - 1) == opponent) {
                    potential = new Position(px, py);
                    match = true;
                } else {
                    match = false;
                }
            } else if (analyse.charAt(i) == me) {
                if (match) {
                    match = false;
                    liste.add(potential);
                }
            }
            px -= x;
            py -= y;
        }
        return liste;
    }

    /**
     * Ajoute une case et calcule les cases a capturer
     *
     * @param p
     * @param player
     * @return
     */
    public int capture(Position p, Player player) {
        if (!cases.get(p).getClass().equals(CaseDisponible.class)) {
            return 0;
        }
        List<Position> liste;
        int nbCapture = 1;
        char me = player.getC().toString().charAt(0);
        char opponent;
        if (player.getC() instanceof CaseBlanche) {
            opponent = new CaseNoire().toString().charAt(0);
        } else {
            opponent = new CaseBlanche().toString().charAt(0);
        }
        cases.put(p, player.getC());
        clearAvailables();
        // Capture de la ligne
        liste = calculCapture(new Position(p.getX(), p.getY() + 1), me, opponent, 0, 1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        liste = calculCapture(new Position(p.getX(), p.getY() - 1), me, opponent, 0, -1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        // Capture de la colonne
        liste = calculCapture(new Position(p.getX() + 1, p.getY()), me, opponent, 1, 0);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        liste = calculCapture(new Position(p.getX() - 1, p.getY()), me, opponent, -1, 0);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        // Capture de la diagonale gauche -> droite
        liste = calculCapture(new Position(p.getX() + 1, p.getY() + 1), me, opponent, 1, 1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        liste = calculCapture(new Position(p.getX() - 1, p.getY() - 1), me, opponent, -1, -1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        // Capture de la diagonale droite -> gauche
        liste = calculCapture(new Position(p.getX() + 1, p.getY() - 1), me, opponent, 1, -1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        liste = calculCapture(new Position(p.getX() - 1, p.getY() + 1), me, opponent, -1, 1);
        if (liste != null) {
            positionsToCases(liste, player.getC());
            nbCapture += liste.size();
        }
        return nbCapture;
    }

    /**
     * Calcul les capture possibles
     *
     * @param p
     * @param me
     * @param opponent
     * @param x
     * @param y
     * @return
     */
    private List<Position> calculCapture(Position p, char me, char opponent, int x, int y) {
        if (p.getX() < 1 || p.getX() > width || p.getY() < 1 || p.getY() > height) {
            return null;
        }
        List<Position> liste = null;
        if (cases.get(p).toString().charAt(0) == me) {
            liste = new ArrayList<>();
            return liste;
        } else if (cases.get(p).toString().charAt(0) == opponent) {
            liste = calculCapture(new Position(p.getX() + x, p.getY() + y), me, opponent, x, y);
            if (liste != null) {
                liste.add(p);
            }
        }
        return liste;
    }

    /**
     * Ajoute la liste de positions dans le plateau avec la case correspondante
     *
     * @param liste
     * @param player
     */
    private void positionsToCases(List<Position> liste, Case player) {
        for (Position element : liste) {
            cases.put(element, player);
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

    // GETTERS AND SETTERS
    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        if (height < DEFAULT_HEIGHT) {
            throw new IllegalArgumentException("La hauteur minimale du plateau doit être de " + DEFAULT_HEIGHT);
        }
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        if (width < DEFAULT_WIDTH) {
            throw new IllegalArgumentException("La largeur minimale du plateau doit être de " + DEFAULT_WIDTH);
        }
        this.width = width;
    }

    public Map<Position, Case> getCases() {
        return cases;
    }
}

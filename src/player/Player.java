package player;

import java.util.Observable;
import structure.Case;
import structure.Plateau;

public abstract class Player extends Observable {

    Case c;

    /**
     * Constructeur
     *
     * @param c
     */
    public Player(Case c) {
        setC(c);
    }

    /**
     * Joue un coup
     *
     * @param p
     */
    public abstract void jouer(Plateau p);

    public Case getC() {
        return c;
    }

    private void setC(Case c) {
        this.c = c;
    }
}

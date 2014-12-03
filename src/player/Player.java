package player;

import java.util.Observable;
import structure.Case;
import structure.Plateau;

public abstract class Player extends Observable {

    Case c;

    /**
     * Constructor
     * @param c 
     */
    public Player(Case c) {
        setC(c);
    }

    /**
     * Get available cases
     * @param p 
     */
    protected void casesAvailable(Plateau p) {
        
    }
    
    public abstract void jouer(Plateau p);

    public Case getC() {
        return c;
    }

    private void setC(Case c) {
        this.c = c;
    }
}

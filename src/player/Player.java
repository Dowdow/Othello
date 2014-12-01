package player;

import java.util.Observable;
import java.util.Observer;
import structure.Case;
import structure.Plateau;

public abstract class Player extends Observable implements Observer {

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
        setChanged();
        notifyObservers("refresh");
    }
    
    /**
     * Is the player turn
     * @param o
     * @return 
     */
    protected boolean isPlayerTurn(Object o) {
        if(o instanceof Case) {
            Case c = (Case) o;
            if(c.equals(getC())) {
                return true;                
            }
        }
        return false;
    }

    public Case getC() {
        return c;
    }

    private void setC(Case c) {
        this.c = c;
    }
}

package player;

import structure.Case;
import structure.Plateau;
import structure.Position;

public abstract class Player {

    Case c;

    public Player(Case c) {
        setC(c);
    }

    public abstract Position jouer(Plateau p);

    public Case getC() {
        return c;
    }

    private void setC(Case c) {
        this.c = c;
    }
}

package player;

import structure.Case;
import structure.Plateau;

public abstract class PlayerIA extends Player {

    public PlayerIA(Case c) {
        super(c);
    }

    @Override
    public abstract int jouer(Plateau p);
    
}

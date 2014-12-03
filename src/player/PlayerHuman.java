package player;

import structure.Case;
import structure.Plateau;

public class PlayerHuman extends Player {

    public PlayerHuman(Case c) {
        super(c);
    }

    @Override
    public void jouer(Plateau p) {
        casesAvailable(p);
        setChanged();
        notifyObservers("refresh");
    }
    
}

package player;

import structure.Case;
import structure.Plateau;

public class PlayerHuman extends Player {

    public PlayerHuman(Case c) {
        super(c);
    }

    @Override
    public int jouer(Plateau p) {
        setChanged();
        notifyObservers("refresh");
        return 0;
    }

}

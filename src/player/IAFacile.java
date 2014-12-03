package player;

import java.util.Random;
import structure.Case;
import structure.Plateau;
import structure.Position;

public class IAFacile extends PlayerIA {

    public IAFacile(Case c) {
        super(c);
    }

    @Override
    public void jouer(Plateau p) {
        casesAvailable(p);
        Random rand = new Random();
        setChanged();
        notifyObservers(new Position(rand.nextInt(7 - 0 + 1) + 0, rand.nextInt(7 - 0 + 1) + 0));
    }

}

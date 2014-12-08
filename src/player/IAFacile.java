package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import structure.Case;
import structure.CaseDisponible;
import structure.Plateau;
import structure.Position;

public class IAFacile extends PlayerIA {

    public IAFacile(Case c) {
        super(c);
    }

    @Override
    public void jouer(Plateau p) {
        List<Position> list = new ArrayList<>();
        Random rand = new Random();

        casesAvailable(p);
        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            if (entrySet.getValue() instanceof CaseDisponible) {
                list.add(entrySet.getKey());
            }

        }

        setChanged();
        notifyObservers(list.get(rand.nextInt(list.size())));
    }

}

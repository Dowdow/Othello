package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import structure.Case;
import structure.CaseDisponible;
import structure.Plateau;
import structure.Position;

public class IAMoyenne extends PlayerIA {

    public IAMoyenne(Case c) {
        super(c);
    }

    @Override
    public void jouer(Plateau p) {
        Plateau pActuel = new Plateau(p);
        Position bestPosition = null;
        int bestScore = 0;
        Random rand = new Random();
        List<Position> cases = new ArrayList<>();

        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            if (entrySet.getValue() instanceof CaseDisponible) {
                cases.add(entrySet.getKey());
            }
        }

        for (Position next : cases) {
            int score = pActuel.capture(next, this);
            if (score > bestScore) {
                bestScore = score;
                bestPosition = next;
            } else if (score == bestScore) {
                if (rand.nextInt(2) == 1) {
                    bestScore = score;
                    bestPosition = next;
                }
            }
            pActuel = new Plateau(p);
        }

        setChanged();
        notifyObservers(bestPosition);
    }

}

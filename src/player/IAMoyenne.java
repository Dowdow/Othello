package player;

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
    public int jouer(Plateau p) {
        Plateau pActuel = new Plateau(p);
        Position bestPosition = null;
        int bestScore = 0;
        Random rand = new Random();

        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            if (entrySet.getValue() instanceof CaseDisponible) {
                int score = pActuel.capture(entrySet.getKey(), this);
                if (score > bestScore) {
                    bestScore = score;
                    bestPosition = entrySet.getKey();
                } else if (score == bestScore) {
                    if (rand.nextInt(2) == 1) {
                        bestScore = score;
                        bestPosition = entrySet.getKey();
                    }
                }
                pActuel = new Plateau(p);
            }
        }

        setChanged();
        notifyObservers(bestPosition);
        return 0;
    }
    
}

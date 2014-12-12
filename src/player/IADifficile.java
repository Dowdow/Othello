package player;

import java.util.Map;
import java.util.Random;
import structure.Case;
import structure.CaseDisponible;
import structure.Plateau;
import structure.Position;

public class IADifficile extends PlayerIA {

    /**
     * Constantes configuration
     */
    public static final int IA_DIFFICILE_SCORE_COINS = 3;
    public static final int IA_DIFFICILE_SCORE_BORDURES = 1;

    public IADifficile(Case c) {
        super(c);
    }

    @Override
    public int jouer(Plateau p) {
        Plateau pActuel = new Plateau(p);
        Position bestPosition = null;
        int bestScore = 0;
        Random rand = new Random();

        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            Position position = entrySet.getKey();
            if (entrySet.getValue() instanceof CaseDisponible) {
                int score = pActuel.capture(position, this);
                if ((position.getX() == 1 && position.getY() == 1)
                        || (position.getX() == p.getHeight() && position.getY() == 1)
                        || (position.getX() == 1 && position.getY() == p.getWidth())
                        || (position.getX() == p.getHeight() && position.getY() == p.getWidth())) {
                    score += IA_DIFFICILE_SCORE_COINS;
                } else if (position.getX() == 1
                        || position.getY() == 1
                        || position.getX() == p.getHeight()
                        || position.getY() == p.getWidth()) {
                    score += IA_DIFFICILE_SCORE_BORDURES;
                }
                if (score > bestScore) {
                    bestScore = score;
                    bestPosition = position;
                } else if (score == bestScore) {
                    if (rand.nextInt(2) == 1) {
                        bestScore = score;
                        bestPosition = position;
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

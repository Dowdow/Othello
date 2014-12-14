package player;

import java.util.HashMap;
import java.util.Map;
import structure.Case;
import structure.CaseDisponible;
import structure.Plateau;
import structure.Position;

public class IAExpert extends PlayerIA {

    public static final int PROFONDEUR_MAX = 4;

    private boolean myTurn;
    private int profondeur;
    private Map<Position, IAExpert> fils = new HashMap<>();

    public IAExpert(Case c) {
        super(c);
        myTurn = true;
        profondeur = 0;
    }

    public IAExpert(Case c, boolean myTurn, int profondeur) {
        super(c);
        this.myTurn = myTurn;
        this.profondeur = profondeur;
    }

    @Override
    public int jouer(Plateau p) {
        Plateau pActuel = new Plateau(p);
        Position bestPosition = null;
        Map<Position, Integer> scores = new HashMap<>();
        int bestScore = - p.getHeight() * p.getWidth();

        for (Map.Entry<Position, Case> entrySet : p.getCases().entrySet()) {
            Position position = entrySet.getKey();
            if (entrySet.getValue() instanceof CaseDisponible) {
                scores.put(position, pActuel.capture(position, this));
                if (profondeur <= PROFONDEUR_MAX) {
                    fils.put(position, new IAExpert(c, !myTurn, profondeur + 1));
                }
                pActuel = new Plateau(p);
            }
        }

        for (Map.Entry<Position, Integer> entrySet : scores.entrySet()) {
            Position position = entrySet.getKey();
            Integer score = entrySet.getValue();
            int scoreFils = 0;
            if (fils.size() > 0) {
                scoreFils = fils.get(entrySet.getKey()).jouer(pActuel);
            }

            if ((position.getX() == 1 && position.getY() == 1)
                    || (position.getX() == p.getHeight() && position.getY() == 1)
                    || (position.getX() == 1 && position.getY() == p.getWidth())
                    || (position.getX() == p.getHeight() && position.getY() == p.getWidth())) {
                score += IADifficile.IA_DIFFICILE_SCORE_COINS;
            } else if (position.getX() == 1
                    || position.getY() == 1
                    || position.getX() == p.getHeight()
                    || position.getY() == p.getWidth()) {
                score += IADifficile.IA_DIFFICILE_SCORE_BORDURES;
            }
            if (myTurn) {
                if (score - scoreFils > bestScore) {
                    bestScore = score - scoreFils;
                    bestPosition = position;
                }
            } else {
                if (score + scoreFils > bestScore) {
                    bestScore = score + scoreFils;
                    bestPosition = position;
                }
            }
        }

        if (profondeur == 0) {
            setChanged();
            notifyObservers(bestPosition);
        }
        return bestScore;
    }

}

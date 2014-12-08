package player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import structure.Case;
import structure.CaseBlanche;
import structure.CaseDisponible;
import structure.CaseNoire;
import structure.Plateau;
import structure.Position;

public abstract class Player extends Observable {

    Case c;

    /**
     * Constructor
     *
     * @param c
     */
    public Player(Case c) {
        setC(c);
    }

    /**
     * Play a player turn
     *
     * @param p
     */
    public abstract void jouer(Plateau p);

    /**
     * Get available cases
     *
     * @param p
     */
    protected void casesAvailable(Plateau p) {
        List<Position> positions = new ArrayList<>();
        int retour;
        Case me = getC();
        Case opponent = null;
        if (me instanceof CaseBlanche) {
            opponent = new CaseNoire();
        } else {
            opponent = new CaseBlanche();
        }
        String regex1 = "V+" + opponent.toString() + "+" + me.toString() + "+";
        String regex2 = me.toString() + "+" + opponent.toString() + "+" + "V+";
        //Parcours de toutes les lignes
        for (int i = 1; i <= p.getHeight(); i++) {
            String ligne = "";
            for (int j = 1; j <= p.getWidth(); j++) {
                ligne += p.getCases().get(new Position(i, j)).toString();
            }
            retour = match(regex1, ligne, true);
            if (retour > 0) {
                positions.add(new Position(i, retour));
            }
            retour = match(regex2, ligne, false);
            if (retour > 0) {
                positions.add(new Position(i, retour));
            }
        }
        //Parcours de toutes les colonnes
        for (int i = 1; i <= p.getWidth(); i++) {
            String colonne = "";
            for (int j = 1; j <= p.getHeight(); j++) {
                colonne += p.getCases().get(new Position(j, i)).toString();
            }
            retour = match(regex1, colonne, true);
            if (retour > 0) {
                positions.add(new Position(retour, i));
            }
            retour = match(regex2, colonne, false);
            if (retour > 0) {
                positions.add(new Position(retour, i));
            }
        }

        for (Iterator<Position> iterator = positions.iterator(); iterator.hasNext();) {
            p.setCase(iterator.next(), new CaseDisponible());
        }
    }

    private int match(String regex, String ligne, boolean left) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ligne);
        while (matcher.find()) {
            if (left) {
                for (int i = 0; i < ligne.length(); i++) {
                    if (ligne.charAt(i) == 'B' || ligne.charAt(i) == 'N') {
                        return i;
                    }
                }
            } else {
                for (int i = ligne.length() - 1; i >= 0; i--) {
                    if (ligne.charAt(i) == 'B' || ligne.charAt(i) == 'N') {
                        return i + 2;
                    }
                }
            }
        }
        return 0;
    }

    public Case getC() {
        return c;
    }

    private void setC(Case c) {
        this.c = c;
    }
}

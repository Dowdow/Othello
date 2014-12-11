package application;

import java.util.Observable;
import java.util.Observer;
import player.Player;
import player.PlayerHuman;
import player.PlayerIA;
import structure.CaseBlanche;
import structure.CaseNoire;
import structure.Plateau;
import structure.Position;

public class Manager extends Observable implements Observer {

    /**
     * Plateau de jeu
     */
    private Plateau plateau;

    /**
     * Joueur 1
     */
    private Player p1;
    /**
     * Joueur 2
     */
    private Player p2;

    /**
     * Joueur courant
     */
    private Player currentPlayer;
    /**
     * Booleen determinant si la partie est en cours ou non
     */
    private boolean isGameStarted = false;

    /**
     * Constructeur
     */
    public Manager() {
        this.plateau = new Plateau();
        this.p1 = new PlayerHuman(new CaseNoire());
        this.p2 = new PlayerHuman(new CaseBlanche());
        this.p1.addObserver(this);
        this.p2.addObserver(this);
    }

    /**
     * Permet de démarrer la partie
     */
    public void start() {
        if (isGameStarted) {
            return;
        }
        this.currentPlayer = p1;
        this.isGameStarted = true;
        message("Le joueur " + currentPlayer.getC().toString() + " démarre la partie !");
        this.plateau.casesAvailable(currentPlayer);
        currentPlayer.jouer(new Plateau(plateau));
    }

    /**
     * Permet de stopper la partie
     */
    public void stop() {
        if (!isGameStarted) {
            return;
        }
        this.isGameStarted = false;
        this.plateau.initialisation(plateau.getHeight(), plateau.getWidth());
        message("Le partie est terminée !");
        refresh();
    }

    /**
     * Permet de jouer un coup
     *
     * @param p
     */
    public void jouer(Position p) {
        if (!isGameStarted) {
            return;
        }
        // Finalisation du tour
        int nbCapture = plateau.capture(p, currentPlayer);
        message("Le joueur " + currentPlayer.getC().toString() + " joue en " + p.getX() + "-" + p.getY() + " en capturant " + nbCapture + " cases");
        refresh();
        // Préparation du prochain tour
        changerPlayer();
        int caseDispo = this.plateau.casesAvailable(currentPlayer);
        if (caseDispo == 0) {
            message("Le joueur " + currentPlayer.getC().toString() + " n'a plus de coup et doit sauter son tour");
            changerPlayer();
            int caseDispo2 = this.plateau.casesAvailable(currentPlayer);
            if (caseDispo2 == 0) {
                message("Plus personne ne peut jouer la partie est donc terminée");
                end();
                return;
            }
        }
        message("C'est au tour du joueur " + currentPlayer.getC().toString());
        currentPlayer.jouer(new Plateau(plateau));
    }

    /**
     * Termine la partie et renvoit les scores
     */
    private void end() {
        int scoreP1 = plateau.calculScore(p1);
        int scoreP2 = plateau.calculScore(p2);
        if (scoreP1 > scoreP2) {
            message("Le joueur " + p1.getC().toString() + " remporte la partie " + scoreP1 + "-" + scoreP2);
        } else if (scoreP1 < scoreP2) {
            message("Le joueur " + p2.getC().toString() + " remporte la partie " + scoreP2 + "-" + scoreP1);
        } else {
            message("Egalité " + scoreP1 + "-" + scoreP2);
        }
    }

    @Override
    public void update(Observable obs, Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            switch (str) {
                case "refresh":
                    refresh();
                    break;
            }
        }
        if (obs instanceof PlayerIA && obj instanceof Position) {
            Position p = (Position) obj;
            jouer(p);
        }
    }

    private void changerPlayer() {
        if (currentPlayer.equals(p1)) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    /**
     * Notifie les observers qu'ils doivent mettre à jour leur tableau
     */
    private void refresh() {
        setChanged();
        notifyObservers("refresh");
    }

    /**
     * Notifie les observers qu'ils viennent de recevoir une information
     *
     * @param message
     */
    private void message(String message) {
        setChanged();
        notifyObservers("message:" + message);
    }

    // GETTERS AND SETTERS
    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        if (isGameStarted) {
            return;
        }
        this.plateau = plateau;
        this.plateau.initialisation(this.plateau.getHeight(), this.plateau.getWidth());
        message("La nouvelle taille du plateau est " + this.plateau.getHeight() + "x" + this.plateau.getWidth());
        refresh();
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        if (isGameStarted) {
            return;
        }
        this.p1 = p1;
        p1.addObserver(this);
        message("Le joueur 1 a été modifié");
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        if (isGameStarted) {
            return;
        }
        this.p2 = p2;
        p2.addObserver(this);
        message("Le joueur 2 a été modifié");
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isIsGameStarted() {
        return isGameStarted;
    }

}

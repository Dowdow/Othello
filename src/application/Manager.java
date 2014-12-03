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

    private Plateau plateau;

    private Player p1;
    private Player p2;

    private Player currentPlayer;
    private boolean isGameStarted = false;

    public Manager() {
        this.plateau = new Plateau();
        this.p1 = new PlayerHuman(new CaseNoire());
        this.p2 = new PlayerHuman(new CaseBlanche());
        this.p1.addObserver(this);
        this.p2.addObserver(this);
    }

    public void start() {
        if (isGameStarted) {
            return;
        }
        this.currentPlayer = p1;
        this.isGameStarted = true;
        currentPlayer.jouer(plateau);
    }

    public void stop() {
        if (!isGameStarted) {
            return;
        }
        this.isGameStarted = false;
        this.plateau.initialisation(plateau.getHeight(), plateau.getWidth());
        refresh();
    }

    public void jouer(Position p) {
        if (!isGameStarted) {
            return;
        }
        plateau.setCase(p, currentPlayer.getC());
        refresh();
        if (currentPlayer.equals(p1)) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
        currentPlayer.jouer(plateau);
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

    private void refresh() {
        setChanged();
        notifyObservers("refresh");
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        if (isGameStarted) {
            return;
        }
        this.plateau = plateau;
        setChanged();
        notifyObservers("refresh");
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
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isIsGameStarted() {
        return isGameStarted;
    }

}

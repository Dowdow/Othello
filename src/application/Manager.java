package application;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import player.IAFacile;
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

    public Manager() {
        this.plateau = new Plateau();
        this.p1 = new PlayerHuman(new CaseNoire());
        this.p2 = new IAFacile(new CaseBlanche());

        this.addObserver(p1);
        this.addObserver(p2);
        this.p1.addObserver(this);
        this.p2.addObserver(this);
    }

    public Manager(Plateau p, Player p1, Player p2) {
        this.plateau = p;
        this.p1 = p1;
        this.p2 = p2;

        this.addObserver(p1);
        this.addObserver(p2);
        this.p1.addObserver(this);
        this.p2.addObserver(this);
    }

    public void start() {
        plateau.initialisation(plateau.getHeight(), plateau.getWidth());
        this.currentPlayer = p1;
        setChanged();
        notifyObservers(currentPlayer.getC());
    }

    public void stop() {
    }

    public void jouer(Position p) {
        System.out.println(currentPlayer.toString());
        plateau.setCase(p, currentPlayer.getC());
        if (currentPlayer.equals(p1)) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
        setChanged();
        notifyObservers(currentPlayer.getC());
    }

    public void getBoard() {
        setChanged();
        notifyObservers(plateau);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            switch (str) {
                case "refresh":
                    getBoard();
                    break;
            }
        }
        if (obs instanceof PlayerIA && obj instanceof Position) {
            Position p = (Position) obj;
            jouer(p);
        }
    }

    public void quitter() {
        setChanged();
        notifyObservers("quit");
    }
}

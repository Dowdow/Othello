package application;

import java.util.Observable;
import player.Player;
import structure.Plateau;

public class Manager extends Observable {

    private Plateau plateau;

    private Player p1;
    private Player p2;

    public Manager(Player p1, Player p2) {
        plateau = new Plateau();
        this.p1 = p1;
        this.p2 = p2;
    }

    public Manager(Plateau p, Player p1, Player p2) {
        this.plateau = p;
        this.p1 = p1;
        this.p2 = p2;
    }

    public void start() {
        while (!plateau.isGameEnded()) {
            plateau.setCase(p1.jouer(plateau), p1.getC());
            plateau.setCase(p2.jouer(plateau), p2.getC());
        }
    }

    public void stop() {
    }

    public void quitter() {
        setChanged();
        notifyObservers("Quitter");
    }

}

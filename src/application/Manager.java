package application;

import player.Player;
import structure.Plateau;

public class Manager {
    
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
    
}

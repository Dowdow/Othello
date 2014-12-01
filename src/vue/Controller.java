package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import application.Manager;
import java.util.Observer;
import player.Player;
import structure.Position;

public class Controller implements ActionListener {

    private Manager manager;

    private Player player1;
    private Player player2;

    public Controller(Observer o) {
        manager = new Manager();
        manager.addObserver(o);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JMenuItem) {
            JMenuItem jmi = (JMenuItem) ae.getSource();
            switch (jmi.getActionCommand()) {
                case "quit":
                    manager.quitter();
                    break;
                case "start":
                    manager.start();
                    break;
                case "stop":
                    manager.stop();
                    break;
            }
        }

        if (ae.getSource() instanceof JButtonPosition) {
            JButtonPosition jbu = (JButtonPosition) ae.getSource();
            manager.jouer(new Position(jbu.getPositionX(), jbu.getPositionY()));
        }
    }
    
    public void getBoard() {
        manager.getBoard();
    }
}

package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import player.PlayerHuman;
import structure.CaseBlanche;
import structure.CaseNoire;
import application.Manager;

public class Controller implements ActionListener {

    private Manager manager;

    public Controller(Window window) {
        manager = new Manager(new PlayerHuman(new CaseNoire()), new PlayerHuman(new CaseBlanche()));
        manager.addObserver(window);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JMenuItem) {
            JMenuItem jmi = (JMenuItem) ae.getSource();
            switch (jmi.getText()) {
                case "Quitter":
                    manager.quitter();
                    break;
                case "Lancer":
                    manager.start();
                    break;
                case "Stopper":
                    manager.stop();
                    break;
            }
        }
        
        if (ae.getSource() instanceof JButton) {
        	JButton jbu = (JButton) ae.getSource();
        	jbu.setBackground(null);
        }
    }
}

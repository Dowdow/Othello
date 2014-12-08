package vue;

import application.Manager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import player.IAFacile;
import player.PlayerHuman;
import structure.Case;
import structure.CaseBlanche;
import structure.CaseNoire;
import structure.Plateau;
import structure.Position;

@SuppressWarnings("serial")
public class Window extends JFrame implements Observer, ActionListener {

    private Manager manager;

    private Modal modal;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exit;
    private JMenu gameMenu;
    private JMenuItem start;
    private JMenuItem stop;
    private JMenu boardMenu;
    private JMenuItem boardParameters;
    private JMenu playerMenu;
    private JMenu player1;
    private JMenu player2;
    private JPanel pGame;
    private List<JButtonPosition> cases = new ArrayList<>();

    /**
     * Constructor
     */
    public Window() {
        super("Othello");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        manager = new Manager();
        create();
        place();
        manager.addObserver(this);
        refreshBoard(manager.getPlateau());
    }

    /**
     * Create elements
     */
    private void create() {
        modal = new Modal(this);

        menuBar = new JMenuBar();

        fileMenu = new JMenu("Fichier");
        menuBar.add(fileMenu);
        exit = new JMenuItem("Quitter");
        exit.setActionCommand("quit");
        exit.addActionListener(this);
        fileMenu.add(exit);

        gameMenu = new JMenu("Jeu");
        menuBar.add(gameMenu);
        start = new JMenuItem("Lancer");
        start.setActionCommand("start");
        start.addActionListener(this);
        gameMenu.add(start);
        stop = new JMenuItem("Stopper");
        stop.setEnabled(false);
        stop.setActionCommand("stop");
        stop.addActionListener(this);
        gameMenu.add(stop);

        boardMenu = new JMenu("Plateau");
        menuBar.add(boardMenu);
        boardParameters = new JMenuItem("Paramètres");
        boardParameters.setActionCommand("settings");
        boardParameters.addActionListener(this);
        boardMenu.add(boardParameters);

        playerMenu = new JMenu("Joueurs");
        menuBar.add(playerMenu);

        player1 = new JMenu("Joueur 1");
        playerMenu.add(player1);
        ButtonGroup group1 = new ButtonGroup();
        player1.add(createRadioButton("Humain", "p1human", group1, true));
        player1.add(createRadioButton("IA Facile", "p1iafacile", group1, false));

        player2 = new JMenu("Joueur 2");
        playerMenu.add(player2);
        ButtonGroup group2 = new ButtonGroup();
        player2.add(createRadioButton("Humain", "p2human", group2, true));
        player2.add(createRadioButton("IA Facile", "p2iafacile", group2, false));

        pGame = new JPanel();
    }

    /**
     * Place elements
     */
    private void place() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pGame, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
    }

    /**
     * Refresh the board
     *
     * @param size
     */
    private void refreshBoard(Plateau plateau) {
        cases.clear();
        pGame.removeAll();
        pGame.setLayout(new GridLayout(plateau.getHeight(), plateau.getWidth()));

        for (Map.Entry<Position, Case> entry : plateau.getCases().entrySet()) {
            JButtonPosition jbp = new JButtonPosition(entry.getKey().getX(), entry.getKey().getY());
            jbp.setText(entry.getKey().getX() + " - " + entry.getKey().getY());
            jbp.setBackground(entry.getValue().getCouleur());
            jbp.addActionListener(this);
            jbp.setActionCommand("click");
            jbp.setEnabled(entry.getValue().isClickable());
            cases.add(jbp);
            pGame.add(jbp);
        }
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JRadioButtonMenuItem) {
            JRadioButtonMenuItem jrbmi = (JRadioButtonMenuItem) ae.getSource();
            switch (jrbmi.getActionCommand()) {
                case "p1human":
                    manager.setP1(new PlayerHuman(new CaseNoire()));
                    break;
                case "p1iafacile":
                    manager.setP1(new IAFacile(new CaseNoire()));
                    break;
                case "p2human":
                    manager.setP2(new PlayerHuman(new CaseBlanche()));
                    break;
                case "p2iafacile":
                    manager.setP2(new IAFacile(new CaseBlanche()));
                    break;
            }
        } else if (ae.getSource() instanceof JMenuItem) {
            JMenuItem jmi = (JMenuItem) ae.getSource();
            switch (jmi.getActionCommand()) {
                case "quit":
                    this.dispose();
                    break;
                case "start":
                    manager.start();
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    boardMenu.setEnabled(false);
                    playerMenu.setEnabled(false);
                    break;
                case "stop":
                    manager.stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    boardMenu.setEnabled(true);
                    playerMenu.setEnabled(true);
                    break;
                case "settings":
                    modal.setVisible(true);
                    modal.addValidateListener(this);
                    break;
            }
        } else if (ae.getSource() instanceof JButton) {
            JButton bt = (JButton) ae.getSource();
            switch (bt.getActionCommand()) {
                case "modal":
                    manager.setPlateau(new Plateau(modal.getSp1Value(), modal.getSp2Value()));
                    modal.setVisible(false);
                    break;
                case "click":
                    JButtonPosition jbu = (JButtonPosition) ae.getSource();
                    manager.jouer(new Position(jbu.getPositionX(), jbu.getPositionY()));
                    break;
            }
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String str = o1.toString();
            switch (str) {
                case "refresh":
                    refreshBoard(manager.getPlateau());
                    break;
            }

        }
    }

    private JRadioButtonMenuItem createRadioButton(String name, String actionCommand, ButtonGroup group, boolean isSelected) {
        JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(name);
        jrbmi.setActionCommand(actionCommand);
        jrbmi.setSelected(isSelected);
        jrbmi.addActionListener(this);
        group.add(jrbmi);
        return jrbmi;
    }

}

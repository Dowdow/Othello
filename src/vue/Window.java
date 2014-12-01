package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
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
import structure.Case;
import structure.CaseBlanche;
import structure.CaseNoire;
import structure.CaseVide;
import structure.Plateau;
import structure.Position;

@SuppressWarnings("serial")
public class Window extends JFrame implements Observer, ActionListener {
    
    private Controller controller;
    
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
    private List<JButtonPosition> cases = new ArrayList<>();

    /**
     * Constructor
     */
    public Window() {
        super("Othello");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        controller = new Controller(this);
        create();
        place();
        controller.getBoard();
    }

    /**
     * Create elements
     */
    private void create() {
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("Fichier");
        menuBar.add(fileMenu);
        exit = new JMenuItem("Quitter");
        exit.setActionCommand("quit");
        exit.addActionListener(controller);
        fileMenu.add(exit);
        
        gameMenu = new JMenu("Jeu");
        menuBar.add(gameMenu);
        start = new JMenuItem("Lancer");
        start.setActionCommand("start");
        start.addActionListener(controller);
        gameMenu.add(start);
        stop = new JMenuItem("Stopper");
        stop.setActionCommand("stop");
        stop.addActionListener(controller);
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
        JRadioButtonMenuItem radio = new JRadioButtonMenuItem("Humain");
        radio.setSelected(true);
        player1.add(radio);
        group1.add(radio);
        radio = new JRadioButtonMenuItem("IA");
        player1.add(radio);
        group1.add(radio);
        
        ButtonGroup group2 = new ButtonGroup();
        player2 = new JMenu("Joueur 2");
        playerMenu.add(player2);
        radio = new JRadioButtonMenuItem("Humain");
        radio.setSelected(true);
        player2.add(radio);
        group2.add(radio);
        radio = new JRadioButtonMenuItem("IA");
        player2.add(radio);
        group2.add(radio);
    }

    /**
     * Place elements
     */
    private void place() {
        this.getContentPane().setLayout(new BorderLayout());
        this.setJMenuBar(menuBar);
    }

    /**
     * Init the board
     *
     * @param size
     */
    private void refreshBoard(Plateau plateau) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(plateau.getHeight(), plateau.getWidth()));
        this.getContentPane().add(panel, BorderLayout.CENTER);
        
        cases.clear();
        panel.removeAll();
        for (Map.Entry<Position, Case> entry : plateau.getCases().entrySet()) {
            JButtonPosition jbp = new JButtonPosition(entry.getKey().getX(), entry.getKey().getY());
            jbp.setText(entry.getKey().getX() + " - " + entry.getKey().getY());
            jbp.setBackground(entry.getValue().getCouleur());
            jbp.addActionListener(controller);
            jbp.setEnabled(entry.getValue().isClickable());
            cases.add(jbp);
            panel.add(jbp);
        }
        this.revalidate();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Modal m = new Modal(this);
        m.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String str = o1.toString();
            switch (str) {
                case "quit":
                    this.dispose();
                    break;
            }
        } else if (o1 instanceof Plateau) {
            Plateau p = (Plateau) o1;
            refreshBoard(p);
        }
    }
    
}

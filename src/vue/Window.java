package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Window extends JFrame implements Observer {
    
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
    
    public Window() {
        super("Othello");
        controller = new Controller(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        create();
        place();
    }

    private void create() {
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("Fichier");
        menuBar.add(fileMenu);
        exit = new JMenuItem("Quitter");
        exit.addActionListener(controller);
        fileMenu.add(exit);
        
        gameMenu = new JMenu("Jeu");
        menuBar.add(gameMenu);
        start = new JMenuItem("Lancer");
        start.addActionListener(controller);
        gameMenu.add(start);
        stop = new JMenuItem("Stopper");
        stop.addActionListener(controller);
        gameMenu.add(stop);
        
        boardMenu = new JMenu("Plateau");
        menuBar.add(boardMenu);
        boardParameters = new JMenuItem("Paramètres");
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
        player2.add(radio);
        group2.add(radio);
        radio = new JRadioButtonMenuItem("IA");
        radio.setSelected(true);
        player2.add(radio);
        group2.add(radio);
    }
    
    private void place() {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(new BorderLayout());
    }

    @Override
    public void update(Observable o, Object o1) {
        if(o1 instanceof String) {
            String str = o1.toString();
            if (str.equals("Quitter")) {
                this.dispose();
            }
        }
    }
}

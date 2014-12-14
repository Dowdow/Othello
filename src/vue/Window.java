package vue;

import application.Manager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import player.IADifficile;
import player.IAExpert;
import player.IAFacile;
import player.IAMoyenne;
import player.PlayerHuman;
import structure.Case;
import structure.CaseBlanche;
import structure.CaseNoire;
import structure.Plateau;
import structure.Position;

@SuppressWarnings("serial")
public class Window extends JFrame implements Observer, ActionListener, ChangeListener {

    private Manager manager;
    private Thread t;
    private Modal modal;
    private JSlider vitesse;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exit;
    private JMenu gameMenu;
    private JMenuItem start;
    private JMenuItem stop;
    private JMenu boardMenu;
    private JMenuItem boardParameters;
    private JMenu player1;
    private JMenu player2;
    private JPanel pGame;
    private JScrollPane pnLogs;
    private JTextArea logs;
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
        pGame = new JPanel();

        vitesse = new JSlider(JSlider.HORIZONTAL, 0, 2000, 500);
        vitesse.setMajorTickSpacing(1000);
        vitesse.setMinorTickSpacing(100);
        vitesse.setPaintTicks(true);
        vitesse.setPaintLabels(true);
        vitesse.addChangeListener(this);

        logs = new JTextArea();
        logs.setFont(new Font("Arial", Font.PLAIN, 14));
        logs.setEditable(false);
        logs.setLineWrap(true);
        logs.setWrapStyleWord(true);

        pnLogs = new JScrollPane(logs);
        pnLogs.setVerticalScrollBar(new JScrollBar());
        pnLogs.setPreferredSize(new Dimension(200, 200));

        ButtonGroup group1 = new ButtonGroup();
        player1 = new JMenu("Joueur 1");
        player1.add(createRadioButton("Humain", "p1human", group1, true));
        player1.add(createRadioButton("IA Facile", "p1iafacile", group1, false));
        player1.add(createRadioButton("IA Moyenne", "p1iamoyenne", group1, false));
        player1.add(createRadioButton("IA Difficile", "p1iadifficile", group1, false));
        player1.add(createRadioButton("IA Expert", "p1iaexpert", group1, false));

        ButtonGroup group2 = new ButtonGroup();
        player2 = new JMenu("Joueur 2");
        player2.add(createRadioButton("Humain", "p2human", group2, true));
        player2.add(createRadioButton("IA Facile", "p2iafacile", group2, false));
        player2.add(createRadioButton("IA Moyenne", "p2iamoyenne", group2, false));
        player2.add(createRadioButton("IA Difficile", "p2iadifficile", group2, false));
        player2.add(createRadioButton("IA Expert", "p2iaexpert", group2, false));

        fileMenu = new JMenu("Fichier");
        exit = createJMenuItem("Quitter", "quit", true);
        fileMenu.add(exit);

        gameMenu = new JMenu("Jeu");
        start = createJMenuItem("Lancer", "start", true);
        gameMenu.add(start);
        stop = createJMenuItem("Stopper", "stop", false);
        gameMenu.add(stop);

        boardMenu = new JMenu("Plateau");
        boardParameters = createJMenuItem("Paramètres", "settings", true);
        boardMenu.add(boardParameters);

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(boardMenu);
        menuBar.add(player1);
        menuBar.add(player2);
    }

    /**
     * Place elements
     */
    private void place() {
        JPanel pnVitesse = new JPanel(new GridLayout(2, 1));
        pnVitesse.setPreferredSize(new Dimension(200, 80));
        pnVitesse.add(new JLabel("Vitesse"));
        pnVitesse.add(vitesse);

        JPanel pnEast = new JPanel(new BorderLayout());
        pnEast.add(pnVitesse, BorderLayout.NORTH);
        pnEast.add(pnLogs, BorderLayout.CENTER);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pGame, BorderLayout.CENTER);
        this.getContentPane().add(pnEast, BorderLayout.EAST);
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
                case "p1iamoyenne":
                    manager.setP1(new IAMoyenne(new CaseNoire()));
                    break;
                case "p1iadifficile":
                    manager.setP1(new IADifficile(new CaseNoire()));
                    break;
                case "p1iaexpert":
                    manager.setP1(new IAExpert(new CaseNoire()));
                    break;
                case "p2human":
                    manager.setP2(new PlayerHuman(new CaseBlanche()));
                    break;
                case "p2iafacile":
                    manager.setP2(new IAFacile(new CaseBlanche()));
                    break;
                case "p2iamoyenne":
                    manager.setP2(new IAMoyenne(new CaseBlanche()));
                    break;
                case "p2iadifficile":
                    manager.setP2(new IADifficile(new CaseBlanche()));
                    break;
                case "p2iaexpert":
                    manager.setP2(new IAExpert(new CaseBlanche()));
                    break;
            }
        } else if (ae.getSource() instanceof JMenuItem) {
            JMenuItem jmi = (JMenuItem) ae.getSource();
            switch (jmi.getActionCommand()) {
                case "quit":
                    this.dispose();
                    break;
                case "start":
                    t = new Thread(manager);
                    t.start();
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    boardMenu.setEnabled(false);
                    player1.setEnabled(false);
                    player2.setEnabled(false);
                    break;
                case "stop":
                    manager.stop();
                     {
                        try {
                            t.join();
                        } catch (InterruptedException ex) {
                            System.err.println("Erreur lors de l'interruption du Thread");
                        }
                    }
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    boardMenu.setEnabled(true);
                    player1.setEnabled(true);
                    player2.setEnabled(true);
                    logs.setText("");
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
    public void stateChanged(ChangeEvent ce) {
        if (ce.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) ce.getSource();
            manager.setSleeptime(slider.getValue());
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String[] str = o1.toString().split(":");
            switch (str[0]) {
                case "refresh":
                    refreshBoard(manager.getPlateau());
                    break;
                case "message":
                    logs.append(str[1] + "\n");
                    logs.setCaretPosition(logs.getDocument().getLength());
                    break;
            }

        }
    }

    private JMenuItem createJMenuItem(String name, String action, boolean enabled) {
        JMenuItem jmi = new JMenuItem(name);
        jmi.setActionCommand(action);
        jmi.setEnabled(enabled);
        jmi.addActionListener(this);
        return jmi;
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

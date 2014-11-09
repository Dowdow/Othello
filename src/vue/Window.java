package vue;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends JFrame {
    
    private Controller controller;
    
    public Window() {
        super("Othello");
        controller = new Controller();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
    }
    
}

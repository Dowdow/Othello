package vue;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Modal extends JDialog {
    
    private JLabel lb1, lb2;
    private JSpinner sp1, sp2;
    private JButton bt;
    
    public Modal(JFrame parent) {
        super(parent);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, 150));
        this.setLocationRelativeTo(parent);
        create();
        place();
    }
    
    private void create() {
        lb1 = new JLabel("Hauteur");
        lb2 = new JLabel("Largeur");
        
        SpinnerModel model1 = new SpinnerNumberModel(8, 8, 20, 1);
        sp1 = new JSpinner(model1);
        SpinnerModel model2 = new SpinnerNumberModel(8, 8, 20, 1);
        sp2 = new JSpinner(model2);
        
        bt = new JButton("Valider");
    }
    
    private void place() {
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(lb1);
        p1.add(sp1);
        
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(lb2);
        p2.add(sp2);
        
        this.getContentPane().setLayout(new GridLayout(3, 1));
        this.getContentPane().add(p1);
        this.getContentPane().add(p2);
        this.getContentPane().add(bt);
    }
    
}

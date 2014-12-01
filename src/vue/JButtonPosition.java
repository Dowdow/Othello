package vue;

import javax.swing.JButton;

public class JButtonPosition extends JButton {
    
    private int positionX;
    private int positionY;
    
    public JButtonPosition(int x, int y) {
        setPositionX(x);
        setPositionY(y);
    }
    
    public int getPositionX() {
        return this.positionX;
    }
    
    private void setPositionX(int x) {
        if(x < 0) {
            throw new IllegalArgumentException("La valeur doit être supérieure à 0 !");
        }
        this.positionX = x;
    }
    
    public int getPositionY() {
        return this.positionY;
    }
    
    private void setPositionY(int y) {
        if(y < 0) {
            throw new IllegalArgumentException("La valeur doit être supérieure à 0 !");
        }
        this.positionY = y;
    }
}

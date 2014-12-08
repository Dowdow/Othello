package structure;

import java.awt.Color;


public class CaseDisponible extends Case {

    @Override
    public Color getCouleur() {
        return Color.decode("#0074d9");
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public String toString() {
        return "D";
    }
    
    
    
}

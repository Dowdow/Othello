package structure;

import java.awt.Color;

public class CaseVide extends Case {

    @Override
    public Color getCouleur() {
        return Color.decode("#2ecc40");
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public String toString() {
        return "V";
    }

    
    
}

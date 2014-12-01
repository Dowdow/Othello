package structure;

import java.awt.Color;


public class CaseDisponible extends Case {

    @Override
    public Color getCouleur() {
        return Color.BLUE;
    }

    @Override
    public boolean isClickable() {
        return true;
    }
    
}

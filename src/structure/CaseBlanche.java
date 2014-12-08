package structure;

import java.awt.Color;

public class CaseBlanche extends Case {

    @Override
    public Color getCouleur() {
        return Color.decode("#ffffff");
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public String toString() {
        return "B";
    }
    
    

}

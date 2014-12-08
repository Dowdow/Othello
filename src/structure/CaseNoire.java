package structure;

import java.awt.Color;

public class CaseNoire extends Case {

    @Override
    public Color getCouleur() {
        return Color.decode("#111111");
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public String toString() {
        return "N";
    }

    
    
}

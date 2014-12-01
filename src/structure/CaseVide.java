package structure;

import java.awt.Color;

public class CaseVide extends Case {

    @Override
    public Color getCouleur() {
        return Color.GREEN;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

}

package structure;

import java.awt.Color;

public class CaseBlanche extends Case {

    @Override
    public Color getCouleur() {
        return Color.WHITE;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

}

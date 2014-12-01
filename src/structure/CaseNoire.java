package structure;

import java.awt.Color;

public class CaseNoire extends Case {

    @Override
    public Color getCouleur() {
        return Color.BLACK;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

}

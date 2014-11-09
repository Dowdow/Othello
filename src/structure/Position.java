package structure;

public class Position {

    private int x;
    private int y;

    /**
     * Constructor
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Return a boolean that mean if the two objects are equal or not
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            if (p.getX() == this.x && p.getY() == this.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        return hash;
    }

    // GETTERS AND SETTERS
    
    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }
}

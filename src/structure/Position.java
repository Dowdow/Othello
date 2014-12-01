package structure;

public class Position implements Comparable<Position> {

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
    public int compareTo(Position t) {        
        if(getX() == t.getX()) {
            if(getY() < t.getY()) {
                return -1;
            } else if(getY() > t.getY()) {
                return 1;
            } else {
                return 0;
            }
        } else if (getX() < t.getX()) {
            return -1;
        } else {
            return 1;
        }
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

public class Cell {
    private int x, y;
    private CellEntityType type;
    private boolean visited;
    public Cell(int x, int y, CellEntityType type, boolean visited) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.visited = visited;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString() {
        return "(" + x + ", " + y + ") - " + type;
    }
}

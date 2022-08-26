package chaselogic;

public class Enemy{
    public int x;
    public int y;

    Enemy(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void appendX(int x) {
        this.x += x;
    }

    public void appendY(int y) {
        this.y += y;
    }
}

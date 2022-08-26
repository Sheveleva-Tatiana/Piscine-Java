package chaselogic;

public class Player{
    public int x;
    public int y;

    Player(int y, int x) {
        this.x = x;
        this.y = y;
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

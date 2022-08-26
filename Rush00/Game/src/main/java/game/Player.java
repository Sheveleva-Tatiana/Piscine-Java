package game;


public class Player implements moved_object{
    public int x;
    public int y;

    Player() {
        x = Map.getPlayerX();
        y = Map.getPlayerY();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void appendX(int x) {
        this.x += x;
    }

    @Override
    public void appendY(int y) {
        this.y += y;
    }
}

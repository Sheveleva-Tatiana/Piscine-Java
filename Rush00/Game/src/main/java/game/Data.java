package game;

import com.diogonunes.jcolor.Attribute;

public class Data {
    public char player;
    public char enemy;
    public char wall;
    public char empty;
    public char goal;
    public Attribute enemyColor = Attribute.RED_BACK();
    public Attribute playerColor = Attribute.GREEN_BACK();
    public Attribute wallColor = Attribute.MAGENTA_BACK();
    public Attribute goalColor = Attribute.BLUE_BACK();
    public Attribute emptyColor = Attribute.YELLOW_BACK();

    Data(){
       player = Map.getPLAYER();
       enemy = Map.getENEMY();
       wall = Map.getWALL();
       empty = Map.getEMPTY();
       goal = Map.getGOAL();
    }
}

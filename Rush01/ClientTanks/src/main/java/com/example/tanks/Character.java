package com.example.tanks;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Character {
    private Image image;
    private LevelLive levelLive;
    private double positionX;
    private double positionY;
    private double width;
    private double height;


    public Character(String filename, double positionX, double positionY, Group root, double posXll, double posYll, GraphicsContext gc) {
        this.positionX = positionX;
        this.positionY = positionY;
        levelLive = new LevelLive(root, posXll, posYll, gc);
        setImage(new Image(filename));
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }


    public void moveLeft(int speed) {
            positionX -= speed;
    }

    public void moveRight(int speed) {
            positionX += speed;
    }

    public boolean canMove(String move) {
        if (move.equals("right")) {
            if (positionX + 10 < 970)
                return true;
        } else {
            if (positionX - 10 > -10)
                return true;
        }
        return false;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void render(GraphicsContext gc)    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public void takeDamage() {
        levelLive.takeDamage();
    }

    public void kill() {
        levelLive.setLevelLife(-10);
    }

    public boolean checkLeaveGame() {
        if (levelLive.getLevelLife() == -10)
            return true;
        return false;
    }

    public boolean checkLife() {
        if (levelLive.getLevelLife() > 0) {
            return true;
        }
        return false;
    }

}

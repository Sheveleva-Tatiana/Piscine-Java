package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
    private char white;
    private char black;
    private BufferedImage image;

    public Logic(char white, char black, String filepath) throws IOException {
        this.white = white;
        this.black = black;
        image = ImageIO.read(new File(filepath));
    }

    public void print() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                if (pixel == Color.WHITE.getRGB()) {
                    System.out.print(white);
                } else if (pixel == Color.BLACK.getRGB()) {
                    System.out.print(black);
                }
            }
            System.out.println();
        }
    }
}
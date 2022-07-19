package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Logic {
    private String white;
    private String black;
    private BufferedImage image;

    public Logic(Args args, String filepath) throws IOException {
        this.white = agrs.getWhite();
        this.black = args.getBlack();
        image = ImageIO.read(Logic.class.getResource(filepath));
    }

    public void print() {
        ColoredPrinter printer = new ColoredPrinter();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                if (pixel == Color.WHITE.getRGB()) {
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(white));
                } else if (pixel == Color.BLACK.getRGB()) {
                    printer.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(black));
                }
            }
            System.out.println();
        }
    }
}
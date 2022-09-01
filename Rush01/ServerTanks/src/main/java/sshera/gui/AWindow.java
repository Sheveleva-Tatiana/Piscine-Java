package sshera.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AWindow {
    public JFrame frame;


    public AWindow(JFrame frame, int width, int height) {
        this.frame = frame;
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public JLabel createLabel(String text, Font font){
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public ImageIcon createIcon(String file) {
        URL imgURL = AWindow.class.getResource(file);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + file);
            return null;
        }
    }

    private JButton createButtons(String text) {
        JButton button = new JButton(text);
        return button;
    }

    public boolean isVisible() {
        return frame.isVisible();
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }

}

package sshera.gui;

import sshera.gui.Models.BoxLayoutUtils;
import sshera.gui.Models.GUITools;

import javax.swing.*;
import java.awt.*;

public class EditConfigurationWindow extends AWindow{
    private JButton buttonOk;
    private JTextField textPort, textHost;

    public JTextField getTextPort() {
        return textPort;
    }

    public JTextField getTextHost() {
        return textHost;
    }

    public EditConfigurationWindow(JFrame frame, int width, int height) {
        super(frame, width, height);
        this.frame = frame;
        frame.getContentPane().add(init());
        frame.setVisible(true);
    }

    private JPanel init() {
        JPanel main = BoxLayoutUtils.createVerticalPanel();

        JLabel mainLabelPort = new JLabel("Welcome to Server Tanks.");


        JLabel mainLabelPort2 = new JLabel("Please, enter number for port.");

        JPanel panelForPort = BoxLayoutUtils.createHorizontalPanel();
        JLabel labelPort = new JLabel("Port: ");
        textPort = new JTextField("8000");
        panelForPort.add(Box.createHorizontalStrut(10));
        panelForPort.add(labelPort);
        panelForPort.add(textPort);
        panelForPort.add(Box.createHorizontalStrut(10));


        JPanel panelForButton = new JPanel();
        panelForButton.setLayout(new GridLayout(1,2));
        buttonOk = new JButton("Ok");
        panelForButton.add(Box.createHorizontalStrut(300));
        panelForButton.add(buttonOk);

        main.add(Box.createVerticalStrut(20));
        main.add(mainLabelPort);
        main.add(mainLabelPort2);
        mainLabelPort.setHorizontalAlignment(JLabel.CENTER);
        mainLabelPort2.setHorizontalAlignment(JLabel.CENTER);

        main.add(panelForPort);
        main.add(Box.createVerticalStrut(10));
        main.add(panelForButton);
        main.add(Box.createVerticalStrut(10));

        return main;
    }

    public JButton getButtonOk() {
        return buttonOk;
    }
}

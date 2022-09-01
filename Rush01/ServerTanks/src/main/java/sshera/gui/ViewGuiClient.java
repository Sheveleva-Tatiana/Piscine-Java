package sshera.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGuiClient {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 250;
    private static int port = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                                  STATIC METHODS FOR RECONNECT FROM MAIN
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static int createConfiguration() {
        EditConfigurationWindow edit = new EditConfigurationWindow(new JFrame("Configuration"), WIDTH, HEIGHT);
        JTextField textPort = edit.getTextPort();
        JButton buttonOk = edit.getButtonOk();


        while(edit.isVisible()) {
            buttonOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        port = Integer.parseInt(textPort.getText());
                    } catch (Exception e) {
                        port = 0;
                    }
                    edit.setVisible(false);
                }
            });
        }
        edit.frame.pack();
        return port;
    }

}

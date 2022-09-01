package sshera.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sshera.gui.ViewGuiClient;
import sshera.server.Server;


public class Program {
    public static void main(String[] args) {
        int port = ViewGuiClient.createConfiguration();

        ApplicationContext context = new AnnotationConfigApplicationContext("sshera");
        Server server = context.getBean(Server.class);
        server.start(port);
    }
}
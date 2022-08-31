package sshera.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sshera.server.Server;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("sshera");
        Server server = context.getBean(Server.class);
        server.start(8000);
    }
}
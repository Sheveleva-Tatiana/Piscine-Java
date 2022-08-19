package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.err.println("Specify the server port using --port=");
            System.exit(-1);
        }

        try {
            int port = Integer.parseInt(args[0].substring(7));
            ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
            Server server = context.getBean(Server.class);
            server.start(port);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}

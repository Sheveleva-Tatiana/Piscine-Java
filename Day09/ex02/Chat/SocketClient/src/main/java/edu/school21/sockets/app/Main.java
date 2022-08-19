package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.err.println("Specify the server port using --server-port=");
            System.exit(-1);
        }

        try {
            int port = Integer.parseInt(args[0].substring(14));
            Client client = new Client("127.0.0.1", port);
            client.start();
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
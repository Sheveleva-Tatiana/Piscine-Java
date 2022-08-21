package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int port;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.err.println("Specify the server port using --server-port=");
            System.exit(-1);
        }
        try {
            port = Integer.parseInt(args[0].substring(14));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            newConnection();
            System.out.println("To reconnect, enter \"start\" or \"exit\" for finish program");
            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("start")) {
                if (answer.equalsIgnoreCase("exit")) {
                    scanner.close();
                    System.exit(0);
                }
                answer = scanner.nextLine();
                continue;
            }
        }
    }

    public static void newConnection() {
        try {
            Client client = new Client("127.0.0.1", port);
            client.start();
        } catch (Exception e) {
            System.out.println("Failed to connect");
        }
    }
}
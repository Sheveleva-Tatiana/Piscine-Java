package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;
    private Scanner scanner = new Scanner(System.in);

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void receiveMessage() throws IOException {
        if (reader.hasNextLine()) {
            String message = reader.nextLine();
            System.out.println(message);

            if ("Successful!".equalsIgnoreCase(message)) {
                stop();
            }
        }
    }

    public void sendMessage() {
        if (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            writer.println(message);
        }
    }

    public void start() throws IOException {
        while (true) {
            receiveMessage();
            sendMessage();
        }
    }

    private void stop() {
        try {
            reader.close();
            writer.close();
            scanner.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
}

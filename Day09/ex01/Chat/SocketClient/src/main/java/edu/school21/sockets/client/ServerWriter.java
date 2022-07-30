package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerWriter extends Thread {
    private PrintWriter writer;
    private Scanner scanner = new Scanner(System.in);
    boolean active = true;
    Scanner reader;
    Socket socket;

    public ServerWriter(PrintWriter writer,  Scanner reader, Socket socket) {
        this.writer = writer;
        this.reader = reader;
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            sendMessage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessage() throws IOException {
        while (active) {
            String message = scanner.nextLine();
            writer.println(message);

            if ("exit".equals(message)) {
                break;
            }
        }
        Client.close(writer, reader, socket, 0);
    }
}

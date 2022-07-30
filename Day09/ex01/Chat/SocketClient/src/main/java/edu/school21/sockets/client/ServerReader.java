package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ServerReader extends Thread {
    private Scanner reader;
    private Socket socket;
    private  PrintWriter writer;
    ServerWriter serverWriter;

    public ServerReader(Scanner reader, PrintWriter writer, Socket socket, ServerWriter serverWriter) {
        this.reader = reader;
        this.serverWriter = serverWriter;
        this.writer = writer;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() throws IOException, InterruptedException {
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            System.out.println(message);
        }
        Client.close(writer, reader, socket, -1);
    }
}

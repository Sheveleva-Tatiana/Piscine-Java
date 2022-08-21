package edu.school21.sockets.client;

import edu.school21.sockets.app.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
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
            String getJSON = reader.nextLine();
            JSONMessage jsonMessage = JSONConverter.parseToObject(getJSON);
            String message = jsonMessage.getMessage();
            LocalDateTime time = jsonMessage.getTime();
            if ("Enter username: ".equals(message)) {
                serverWriter.isReadingThree = false;
            }
            if ("Choose command:".equals(message)) {
                serverWriter.canFinish = true;
            }
            if ("1. Create room".equals(message)) {
                serverWriter.canFinish = false;
            }
            if ("Authorization failed!".equals(message) || "Authorization successful!".equals(message) ||
                    message.contains("already exist") || "1. Create room".equals(message) || message.contains("created!")) {
                serverWriter.isReadingThree = true;
            }
            if ("You have left the chat".equals(message)) {
                serverWriter.inRoom = false;
                serverWriter.canFinish = false;
            }
            if (message.contains("Rooms:") || message.contains("---")) {
                serverWriter.isReadingThree = false;
                serverWriter.canFinish = false;
            }
            if (message.contains("---")) {
                serverWriter.inRoom = true;
                serverWriter.canFinish = false;
            }

            System.out.println(message);
        }
        if (serverWriter.active) {
            serverWriter.active = false;
            Client.close(writer, reader, socket, -1);
        }
    }
}

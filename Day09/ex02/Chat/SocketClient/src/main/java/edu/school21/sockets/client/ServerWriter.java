package edu.school21.sockets.client;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerWriter extends Thread {
    private PrintWriter writer;
    private JSONConverter jsonConverter = new JSONConverter();
    private Scanner scanner = new Scanner(System.in);
    boolean active = true;
    Scanner reader;
    Socket socket;
    boolean isReadingThree = true;
    boolean inRoom = false;
    boolean canFinish = true;

    public ServerWriter(PrintWriter writer, Scanner reader, Socket socket) {
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

    private void sendMessage() throws IOException, InterruptedException {
        while (active) {
            String toSendMessage = scanner.nextLine();
            JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);
            String message = messageJSON.toJSONString();
            if (("exit".equals(toSendMessage) && !inRoom && canFinish) ||
                    ("3".equals(toSendMessage) && isReadingThree && canFinish)) {
                active = false;
                Client.close(writer, reader, socket, 0);
            }
            writer.println(message);
        }
        return;
    }
}

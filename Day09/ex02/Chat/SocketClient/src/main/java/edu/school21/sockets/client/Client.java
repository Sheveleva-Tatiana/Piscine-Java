package edu.school21.sockets.client;

import org.w3c.dom.ls.LSOutput;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;

    public Client(String ip, int port) throws IOException {
        socket = SocketFactory.getDefault().createSocket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() throws InterruptedException, IOException {
        ServerWriter serverWriter = new ServerWriter(writer, reader, socket);
        ServerReader serverReader = new ServerReader(reader, writer, socket, serverWriter);
        serverReader.start();
        serverWriter.start();
        serverReader.join();
        serverWriter.join();
    }

    public synchronized static void close(PrintWriter writer,  Scanner reader, Socket socket, int flag) throws IOException {
        writer.close();
        socket.close();
        if (flag == -1) {
            System.out.println("Server connection lost :(");
            return;
        }
        System.exit(flag);
    }
}
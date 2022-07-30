package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Component
public class Server {
    private final UsersService usersService;
    private PrintWriter writer;
    private Scanner reader;
    private ServerSocket serverSocket;
    private Socket socket;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        String message, username, password;

        username = null;
        password = null;
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from Server!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            stop();
        }

        while (true) {
            try {
                if (reader.hasNextLine()) {
                    message = reader.nextLine().trim();
                    if (!"signUp".equalsIgnoreCase(message)) {
                        throw new RuntimeException("Wrong input. Try enter \"signUp\"");
                    }
                }
                writer.println("Enter username: ");

                if (reader.hasNextLine()) {
                    username = reader.nextLine().trim();
                }
                writer.println("Enter password: ");

                if (reader.hasNextLine()) {
                    password = reader.nextLine().trim();
                }
                usersService.signUp(new User(username, password));
                writer.println("Successful!");
                stop();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                writer.println(e.getMessage());
            }
        }
    }

    private void stop() {
        try {
            if (reader != null) {
                reader.close();
            }

            if (writer != null) {
                writer.close();
            }

            if (socket != null) {
                socket.close();
            }

            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
}

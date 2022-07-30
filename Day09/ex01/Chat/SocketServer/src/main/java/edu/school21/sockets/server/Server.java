package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Server {
    private UsersService usersService;
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();
    private int num = 1;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

            WriterThread writerThread = new WriterThread();
            writerThread.start();
            while(true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clients.add(client);
                System.out.println("New client connected! Number of clients: " + num++);
                client.start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            stop();
        }
    }

    private class WriterThread extends Thread {
        Scanner scanner = new Scanner(System.in);

        @Override
        public void run() {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("exit"))
                {
                    Server.this.stop();
                }
            }
        }
    }

    private synchronized void sendMessageToAll(String message) {
        usersService.createMessage(message);
        clients.stream().filter(c -> c.active).forEach(c -> c.writer.println(message));
    }

    private void removeClient(Client client) {
        clients.remove(client);
        num--;
        System.out.println("The user has left the chat.");
    }

    private void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }

    private class Client extends Thread {
        private PrintWriter writer;
        private Scanner reader;
        private Socket socket;
        private String username;
        private String password;
        private boolean active;

        Client(Socket socket) {
            try {
                this.socket = socket;
                reader = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void run() {
            writer.println("Hello from Server!");

            while (true) {
                writer.println("Choose command: signUp, signIn, exit");

                try {
                    if (reader.hasNextLine()) {
                        String message = reader.nextLine().trim();

                        if ("signUp".equalsIgnoreCase(message)) {
                            if (!getUserPass()) {
                                exitChat();
                                break;
                            }
                            usersService.signUp(new User(username, password));
                            writer.println("User: " + username + " created!");
                            continue;
                        } else if ("signIn".equalsIgnoreCase(message)) {
                            if (!getUserPass()) {
                                exitChat();
                                break;
                            }
                            if (usersService.signIn(username, password)) {
                                writer.println("Authorization successful!");
                                System.out.println("Authorization successful for user: " + username);
                                writer.println("Start messaging");
                                talk();
                                break;
                            } else {
                                writer.println("Authorization failed!");
                                System.out.println("Authorization failed for user: " + username);
                            }
                        } else if ("".equals(message)) {
                            continue;
                        } else if ("exit".equals(message)) {
                            exitChat();
                            break;
                        } else {
                            writer.println("Unknown command!");
                        }
                    } else {
                    exitChat();
                    return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    writer.println(e.getMessage());
                }
            }
        }

        private boolean getUserPass() {
            writer.println("Enter username: ");
            username = reader.nextLine().trim();

            while("".equals(username)) {
                username = reader.nextLine().trim();
            }

            if ("exit".equals(username)) {
                return false;
            }
            writer.println("Enter password: ");
            password = reader.nextLine().trim();

            while("".equals(password)) {
                password = reader.nextLine().trim();
            }

            if ("exit".equals(password)) {
                return false;
            }
            return true;
        }

        private void talk() {
            while (true) {
                active = true;
                String message = reader.nextLine().trim();

                if ("exit".equals(message)) {
                    exitChat();
                    break;
                }
                sendMessageToAll(username + ": " + message);
            }
        }

        private void exitChat() {
            try {
                writer.println("exit");
                removeClient(this);
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

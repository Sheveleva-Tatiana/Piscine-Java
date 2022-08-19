package edu.school21.sockets.server;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.RoomsService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class Server {
    private UsersService usersService;
    private RoomsService roomsService;
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();

    private int num = 1;

    @Autowired
    public Server(UsersService usersService, RoomsService roomsService) {
        this.usersService = usersService;
        this.roomsService = roomsService;
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

    private synchronized void sendMessageToRoom(String message, String roomTitles) {
        usersService.createMessage(message);
        clients.stream().filter(title -> Objects.equals(title.roomTitle, roomTitles)).forEach(c -> c.writer.println(message));
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
        private String roomTitle;
        private String password;
        boolean isAuthorized = false;


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

            while (true && !isAuthorized) {
                writer.println("Choose command:");
                writer.println("1. SignUp");
                writer.println("2. SignIn");
                writer.println("3. Exit");

                try {
                    if (reader.hasNextLine()) {
                        String message = reader.nextLine().trim();

                        if ("1".equalsIgnoreCase(message)) {
                            signUpUser();
                        } else if ("2".equalsIgnoreCase(message)) {
                            signInUser();
                            chooseOrCreateRoom();
                        } else if ("".equals(message)) {
                            continue;
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            exitChat();
                            return;
                        } else {
                            writer.println("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                    writer.println(e.getMessage());
                }
            }
        }

        private void chooseOrCreateRoom() {
            while(true) {
                writer.println("1. Create room");
                writer.println("2. Choose room");
                writer.println("3. Exit");
                try {
                    if (reader.hasNextLine()) {
                        String message = reader.nextLine().trim();

                        if ("1".equalsIgnoreCase(message)) {
                            createRoom();
                            talk();
                        } else if ("2".equalsIgnoreCase(message)) {
                            chooseRoom();
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            exitChat();
                            return;
                        } else if ("".equals(message)) {
                            continue;
                        } else {
                            writer.println("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                    writer.println(e.getMessage());
                }
            }


        }

        private void chooseRoom() {
            int numberRoom;
            List<Chatroom> allRooms = roomsService.showAllRooms();
            if (allRooms.isEmpty()) {
                writer.println("No room created!");
                return;
            } else {
                writer.println("Rooms: ");
                for (int i = 0; i < allRooms.size(); i++) {
                    writer.println(i + 1 + ". " + allRooms.get(i).getTitle());
                }
                writer.println(allRooms.size() + 1 + ". Exit");
            }
            while (true) {
                try {
                    numberRoom = Integer.parseInt(reader.nextLine().trim());
                    if (numberRoom <= allRooms.size() && numberRoom > 0) {
                        roomTitle = allRooms.get(numberRoom - 1).getTitle();
                        talk();
                        break;
                    } else if (numberRoom == allRooms.size() + 1) {
                        exitChat();
                        return;
                    } else {
                        writer.println("Wrong number of room. Please, try again.");
                    }
                } catch (Exception e) {
                    writer.println(e.getMessage());
                }
            }
        }

        private void createRoom() {
            writer.println("The process of creating a room has begun...");
            writer.println("Enter Title for new room");
            roomTitle = reader.nextLine().trim();
            while("".equals(roomTitle)) {
                roomTitle = reader.nextLine().trim();
            }
            roomsService.createRoom(new Chatroom(roomTitle, username));
            rooms.add(roomTitle);
            writer.println("Room created!");
        }

        private void signInUser() {
            if (!getUserPass()) {
                exitChat();
            }
            if (usersService.signIn(username, password)) {
                writer.println("Authorization successful!");
                System.out.println("Authorization successful for user: " + username);
                isAuthorized = true;
            } else {
                writer.println("Authorization failed!");
                System.out.println("Authorization failed for user: " + username);
            }
        }

        private void signUpUser() {
            if (!getUserPass()) {
                exitChat();
            }
            usersService.signUp(new User(username, password));
            writer.println("User: " + username + " created!");
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
            writer.println(roomTitle + "---");
            while (true) {
                String message = reader.nextLine().trim();

                if ("exit".equals(message)) {
                    writer.println("You have left the chat");
                    break;
                }
                sendMessageToRoom(username + ": " + message, roomTitle);
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

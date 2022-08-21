package edu.school21.sockets.server;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
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

    private synchronized void sendMessageToRoom(String message, String roomTitles, String senderName) {
        usersService.createMessage(message, senderName, roomTitles);
        clients.stream().filter(title -> Objects.equals(title.roomTitle, roomTitles)).
                forEach(c -> c.sendMsgToClient(senderName + ": " + message));
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
            sendMsgToClient("Hello from Server!");

            while (true) {
                sendMsgToClient("Choose command:");
                sendMsgToClient("1. SignUp");
                sendMsgToClient("2. SignIn");
                sendMsgToClient("3. Exit");

                try {
                    if (reader.hasNextLine()) {
                        String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();

                        if ("1".equalsIgnoreCase(message)) {
                            signUpUser();
                        } else if ("2".equalsIgnoreCase(message)) {
                            if (signInUser()) {
                                chooseOrCreateRoom();
                            }
                        } else if ("".equals(message)) {
                            continue;
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            exitChat();
                            return;
                        } else {
                            sendMsgToClient("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                    sendMsgToClient(e.getMessage());
                }
            }
        }

        private void chooseOrCreateRoom() {
            while(true) {
                sendMsgToClient("1. Create room");
                sendMsgToClient("2. Choose room");
                sendMsgToClient("3. Exit");
                try {
                    if (reader.hasNextLine()) {
                        String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();

                        if ("1".equalsIgnoreCase(message)) {
                            createRoom();
                            talk();
                        } else if ("2".equalsIgnoreCase(message)) {
                            chooseRoom();
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            System.out.println("User: '" + username + "' logged out");
                            sendMsgToClient("Logout for " + username);
                            return;
                        } else if ("".equals(message)) {
                            continue;
                        } else {
                            sendMsgToClient("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                    sendMsgToClient(e.getMessage());
                }
            }
        }

        private void chooseRoom() {
            int numberRoom;
            List<Chatroom> allRooms = roomsService.showAllRooms();
            if (allRooms.isEmpty()) {
                sendMsgToClient("No room created!");
                return;
            } else {
                sendMsgToClient("Rooms: ");
                for (int i = 0; i < allRooms.size(); i++) {
                    sendMsgToClient(i + 1 + ". " + allRooms.get(i).getTitle());
                }
                sendMsgToClient(allRooms.size() + 1 + ". Exit");
            }
            while (true) {
                try {
                    numberRoom = Integer.parseInt(JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON());
                    if (numberRoom <= allRooms.size() && numberRoom > 0) {
                        roomTitle = allRooms.get(numberRoom - 1).getTitle();
                        System.out.println("User '" + username + "' in room: '" + roomTitle + "'");
                        talk();
                        break;
                    } else if (numberRoom == allRooms.size() + 1) {
                        return;
                    } else {
                        sendMsgToClient("Wrong number of room. Please, try again.");
                    }
                } catch (Exception e) {
                    sendMsgToClient(e.getMessage());
                }
            }
        }

        private void createRoom() {
            sendMsgToClient("The process of creating a room has begun...");
            sendMsgToClient("Enter Title for new room");
            roomTitle = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();
            while("".equals(roomTitle)) {
                roomTitle = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();
            }
            roomsService.createRoom(new Chatroom(roomTitle, username));
            rooms.add(roomTitle);
            System.out.println("User: '" + username + "' created room: " + roomTitle);
            sendMsgToClient("Room '" + roomTitle + "' created!");
        }

        private boolean signInUser() {
            if (!getUserPass()) {
                exitChat();
            }
            if (usersService.signIn(username, password)) {
                sendMsgToClient("Authorization successful!");
                System.out.println("Authorization successful for user: " + username);
                Message msglastRoom = usersService.findLastRoom(username);
                String lastRoom;
                if (msglastRoom != null) {
                    lastRoom = msglastRoom.getTitleRoom();
                    sendMsgToClient("______________________________________________");
                    sendMsgToClient("Last time you were in room: '" + lastRoom + "'");
                    showLastThirtyMessage(lastRoom);
                    sendMsgToClient("______________________________________________");

                }
                return true;
            } else {
                sendMsgToClient("Authorization failed!");
                System.out.println("Authorization failed for user: " + username);
                return false;
            }
        }

        private void sendMsgToClient(String text) {
            writer.println(Objects.requireNonNull(JSONConverter.makeJSONObject(text)).toJSONString());
        }

        private void signUpUser() {
            if (!getUserPass()) {
                exitChat();
                return;
            }
            usersService.signUp(new User(username, password));
            System.out.println("User '" + username + "' was created.");
            sendMsgToClient("User: " + username + " created!");
        }

        private boolean getUserPass() {
            sendMsgToClient("Enter username: ");
            username = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();

            while("".equals(username)) {
                username = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();
            }

            if ("exit".equals(username)) {
                return false;
            }
            sendMsgToClient("Enter password: ");
            password = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();

            while("".equals(password)) {
                password = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();
            }

            if ("exit".equals(password)) {
                return false;
            }
            return true;
        }

        private void showLastThirtyMessage(String title) {
            List<Message> allMessage = usersService.getAllMessageByTitle(title);
            if (!allMessage.isEmpty()) {
                for (Message msg : allMessage) {
                    sendMsgToClient(msg.getAuthor() + ": " + msg.getMessage());
                }
            }
        }
        private void talk() {
            sendMsgToClient(roomTitle + "---");
            showLastThirtyMessage(roomTitle);
            while (true) {
                String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageJSON();
                if ("exit".equals(message)) {
                    System.out.println(username + " left room: " + roomTitle);
                    sendMsgToClient("You have left the chat");
                    break;
                }
                sendMessageToRoom(message, roomTitle, username);
            }
        }

        private void exitChat() {
            try {
                sendMsgToClient("exit");
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

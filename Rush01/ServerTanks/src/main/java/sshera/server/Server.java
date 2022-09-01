package sshera.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sshera.service.PointsService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Server {
    private PointsService pointsService;
    private ServerSocket serverSocket;
    private List<Client> clients = new ArrayList<>();
    int num = 0;

    @Autowired
    public Server(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is run");
            while (num != 2) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, ++num);
                clients.add(client);
                System.out.println("New client connected! Number of clients: " + num);
                pointsService.createClient(num);
            }
            clients.forEach(Thread::start);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeClient(Client client) {
        clients.remove(client);
        num--;
        System.out.println("The user has left the game.");
    }

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
                                               CLASS CLIENTS
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    private class Client extends Thread {
        private PrintWriter writer;
        private Scanner reader;
        private Socket socket;
        private int num;


        Client(Socket socket, int num) {
            try {
                this.socket = socket;
                this.num = num;
                reader = new Scanner(socket.getInputStream());
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void run() {
            writer.println("start");
            System.out.println("Client " + num + " start");
            while (true) {
                try {
                    if (reader.hasNextLine()) {
                        String input = reader.nextLine();
                        if (input.equals("outshoot"))
                            pointsService.addShot(num);
                        else if(input.equals("hit"))
                            pointsService.addHit(num);
                        else if (input.equals("gameOver")) {
                            String finalInput2 = input;
                            clients.stream().filter(client -> this.num != client.num).
                                    forEach(c -> c.writer.println(finalInput2));
                            input = pointsService.getStatistics(num);
                            String finalInput = input;
                            writer.println(finalInput);
                            break;
                        }
                        String finalInput1 = input;
                        clients.stream().filter(client -> this.num != client.num).
                                    forEach(c -> c.writer.println(finalInput1));
                    } else {
                        clients.stream().filter(client -> this.num != client.num).
                                forEach(c -> c.writer.println("enemyLeftGame"));
                        exitClient();
                        return;
                    }
                } catch (RuntimeException e) {
                    System.err.println(e);
                }
            }
        }

        private void exitClient() {
            try {
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

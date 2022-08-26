package game;

import chaselogic.EnemyWay;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

public class Logic {
    public static void makeLogic(Data data, char[][] area, Player player, Painter painter, boolean isProd) {
        Enemy[] enemies = new Enemy[Map.getCountEnemies()];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String action;
        int k = 0;
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                if (area[i][j] == data.enemy)
                    enemies[k++] = new Enemy(i, j);
            }
        }
        try {
            painter.drawMap(area, isProd);
            while (true) {
                System.out.println("Enter cmd: ");
                action = reader.readLine();
                if (action.length() != 1)
                    continue;
                if (action.charAt(0) == '9')
                    break;
                if (action.charAt(0) == '\n')
                    continue;
                if (check_player_move(data, area, player, action.charAt(0))) {
                    painter.drawMap(area, isProd);
                    sleep(500);
                    k = 0;
                    for (int i = 0; i < area.length; i++) {
                        for (int j = 0; j < area[i].length; j++) {
                            if (area[i][j] == data.enemy)
                                enemies[k++] = new Enemy(i, j);
                        }
                    }
                    Thread[] th = new Thread[enemies.length];
                    for (int i = 0; i < enemies.length; i++) {
                        if (!isProd) {
                            System.out.println("Enter 8 for enemy step, Enter 9 for out:");
                            while (true) {
                                String ch = reader.readLine();
                                if (ch.length() != 1)
                                    continue;
                                if (ch.charAt(0) == '8')
                                    break;
                                if (ch.charAt(0) == '9') {
                                    reader.close();
                                    System.exit(0);
                                }
                            }
                        }
                        th[i] = new EnemyWay(enemies[i].getY(), enemies[i].getX(), area, player.getY(), player.getX());
                        th[i].start();
                        if (!isProd) {
                            th[i].join();
                            painter.drawMap(area, isProd);
                        }
                    }
                    if (isProd) {
                        for (Thread t : th)
                            t.join();
                    }
                }
                if (!isProd)
                {
                    System.out.println("Enter move key:");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static boolean check_player_move(Data data, char[][] area, moved_object player, char action) {
        switch (action) {
            case 'W':
            case 'w':
                return try_move_player(data, player, area, -1, 0);
            case 'A':
            case 'a':
                return try_move_player(data, player, area, 0, -1);
            case 'S':
            case 's':
                return try_move_player(data, player, area, 1, 0);
            case 'D':
            case 'd':
                return try_move_player(data, player, area, 0, 1);
            default:
                return false;
        }
    }

    public static boolean try_move_player(Data data, moved_object player, char[][] area, int y, int x) {
        char symbol;
        try {
            symbol = area[player.getY() + y][player.getX() + x];
        } catch (Exception ignored) {
            return true;
        }
        if (symbol == data.enemy) {
            System.out.println("Nooo, He was so young");
            System.exit(0);
        }
        if (symbol == data.wall) {
            return true;
        }
        if (symbol == data.goal) {
            System.out.println("Congratulations! You win!!!");
            System.exit(0);
        }
        area[player.getY()][player.getX()] = symbol;
        area[player.getY() + y][player.getX() + x] = data.player;
        player.appendX(x);
        player.appendY(y);
        return true;
    }
}

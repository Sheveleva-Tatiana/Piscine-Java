package chaselogic;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnemyWay extends Thread {
    private Info data = new Info();
    private Enemy enemy;
    private char[][] area;
    private Player player;

    public EnemyWay(int enemyY, int enemyX, char[][] area, int playerY, int playerX) {
        this.enemy = new Enemy(enemyY, enemyX);
        this.area = area;
        this.player = new Player(playerY, playerX);
    }

    @Override
    public void run() {
        int[][] copy = new int[area.length][area[0].length];
        synchronized (area) {
            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area[i].length; j++) {
                    if (area[i][j] == data.player)
                        copy[i][j] = -2;
                    else if (area[i][j] == data.empty)
                        copy[i][j] = 0;
                    else
                        copy[i][j] = -1;
                }
            }
        }
        copy[enemy.getY()][enemy.getX()] = 1;
        int d = 1;
        AtomicBoolean isItWay = new AtomicBoolean(true);
        while (isItWay.get()) {
            isItWay.set(false);
            for (int i = 0; i < copy.length; i++) {
                for (int j = 0; j < copy[i].length; j++) {
                    if (copy[i][j] == d) {
                        if (TryToEnemyMove(copy, i + 1, j, d, isItWay) ||
                                TryToEnemyMove(copy, i + -1, j, d, isItWay) ||
                                TryToEnemyMove(copy, i, j + 1, d, isItWay) ||
                                TryToEnemyMove(copy, i, j + -1, d, isItWay)) {
                            throwEnemyToPlayer(copy, area, enemy, player);
                            return;
                        }
                    }
                }
            }
            d++;
        }
    }

    public void throwEnemyToPlayer(int[][] copy, char[][] area, Enemy enemy, Player player)
    {
        char symbol;
        Point point;

        point = getLessNearPoint(copy, player.getY(), player.getX());
        while (copy[point.getY()][point.getX()] > 2)
        {
            point = getLessNearPoint(copy, point.getY(), point.getX());
        }
        synchronized (area) {
            if (area[point.getY()][point.getX()] == data.enemy)
            {
                return ;
            }
            if (copy[point.getY()][point.getX()] == copy[player.getY()][player.getX()])
            {
                synchronized (System.out) {
                    System.out.println("Congratulations! You are lose!");
                    System.exit(0);
                }
            }
            symbol = area[point.getY()][point.getX()];
            area[point.getY()][point.getX()] = area[enemy.getY()][enemy.getX()];
            area[enemy.getY()][enemy.getX()] = symbol;
        }
    }

    public Point getLessNearPoint(int[][] copy, int y, int x)
    {
        Point point = new Point();
        point.setY(y);
        point.setX(x);
        int min = 0;

        try {
            if (copy[y + 1][x] > 1)
            {
                point.setX(x);
                point.setY(y + 1);
                min = copy[y + 1][x];
            }
        }
        catch (Exception ignored) {}
        try {
            if (copy[y - 1][x] > 1 && (min == 0 || copy[y - 1][x] < min))
            {
                point.setX(x);
                point.setY(y - 1);
                min = copy[y - 1][x];
            }
        }
        catch (Exception ignored) {}
        try {
            if (copy[y][x + 1] > 1 && (min == 0 || copy[y][x + 1] < min))
            {
                point.setX(x + 1);
                point.setY(y);
                min = copy[y][x + 1];
            }
        }
        catch (Exception ignored) {}
        try {
            if (copy[y][x - 1] > 1 && (min == 0 || copy[y][x - 1] < min))
            {
                point.setX(x - 1);
                point.setY(y);
            }
        }
        catch (Exception ignored) {}
        return point;
    }

    public boolean TryToEnemyMove(int[][] copy, int i, int j, int d, AtomicBoolean isItWay) {
        try {
            if (copy[i][j] == -2) {
                copy[i][j] = d + 1;
                return true;
            }
            if (copy[i][j] != -1 && (copy[i][j] == 0 || copy[i][j] > d + 1)) {
                copy[i][j] = d + 1;
                isItWay.set(true);
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}

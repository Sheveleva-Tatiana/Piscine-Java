package game;

import game.exceptions.IllegalParametersException;

import java.util.Random;

public class Map {
    private static char PLAYER = 'P';
    private static char EMPTY = '.';
    private static char ENEMY = 'X';
    private static char GOAL = '!';
    private static char WALL = 'W';

    private static char PATH = 'V';
    private static int countEnemies;

    public static int getCountEnemies() {
        return countEnemies;
    }

    private static int countWalls;
    private static int sizeMap;
    private static int PlayerX;
    private static int PlayerY;

    public static int getPlayerX() {
        return PlayerX;
    }

    public static int getPlayerY() {
        return PlayerY;
    }

    private static int ExitX;
    private static int ExitY;

    private static char[][] map;
    private static Random random = new Random();

    public static void createMap(int enemies, int walls, int size) {
        if (size <= 0 || enemies < 0 || walls < 0 || (size * size) < (walls + enemies + 2)) {
            throw new IllegalParametersException("Invalid input");
        }
        countEnemies = enemies;
        sizeMap = size;
        countWalls = walls;
        generateMap();
        while (!findPath()) {
            generateMap();
        }
        backEmpty();
    }

    private static void backEmpty() {
        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                if (map[i][j] == PATH) {
                    map[i][j] = EMPTY;
                }
            }
        }
    }

    public static char[][] getMap() {
        return map;
    }

    private static void generateMap() {
        initEmptyMap();
        putPlayer();
        putEnemies();
        putExit();
        putWalls();
    }

    private static boolean findPath() {
        if(!checkPlayerAround(ExitY, ExitX, PLAYER)) {
            return true;
        }
        if (PlayerY + 1 < sizeMap && map[PlayerY + 1][PlayerX] == EMPTY)
            map[PlayerY + 1][PlayerX] = PATH;
        if (PlayerX + 1 < sizeMap && map[PlayerY][PlayerX + 1] == EMPTY)
            map[PlayerY][PlayerX + 1] = PATH;
        if (PlayerY - 1 >= 0 && map[PlayerY - 1][PlayerX] == EMPTY)
            map[PlayerY - 1][PlayerX] = PATH;
        if (PlayerX - 1 >= 0 && map[PlayerY][PlayerX - 1] == EMPTY)
            map[PlayerY][PlayerX - 1] = PATH;
        int c = 0;
        while (check() > c) {
            c = check();
            searchValid();
            if (!checkPlayerAround(ExitY, ExitX, PATH))
                return true;
        }
        return false;
    }

    private static void searchValid() {
        for (int y = 0; y < sizeMap; y++){
            for (int x = 0; x < sizeMap; x++){
                if(y + 1 < sizeMap && map[y + 1][x] == PATH &&  map[y][x] == EMPTY)
                    map[y][x] = PATH;
                if(x + 1 < sizeMap && map[y][x + 1] == PATH &&  map[y][x] == EMPTY)
                    map[y][x] = PATH;
                if(y - 1 > 0 && map[y - 1][x] == PATH &&  map[y][x] == EMPTY)
                    map[y][x] = PATH;
                if(x - 1 > 0 && map[y][x - 1] == PATH &&  map[y][x] == EMPTY)
                    map[y][x] = PATH;
            }
        }
    }

    private static int check(){
        int b = 0;
        for(int i = 0; i < sizeMap; i++){
            for (int c = 0; c < sizeMap; c++){
                if (map[i][c] == PATH)
                    b++;
            }
        }
        return b;
    }

    private static void putPlayer() {
        int y = random.nextInt(sizeMap);
        int x = random.nextInt(sizeMap);
        if (map[y][x] == EMPTY) {
            map[y][x] = PLAYER;
            PlayerY = y;
            PlayerX = x;
            return;
        }
        putPlayer();
    }

    private static void putExit() {
        int y = random.nextInt(sizeMap);
        int x = random.nextInt(sizeMap);
        if (map[y][x] == EMPTY) {
            map[y][x] = GOAL;
            ExitX = x;
            ExitY = y;
            return;
        }
        putExit();
    }

    private static void putEnemies() {
        int iterator = 0;
        while (iterator < countEnemies) {
            int y = random.nextInt(sizeMap);
            int x = random.nextInt(sizeMap);
            if (map[y][x] == EMPTY && checkPlayerAround(y, x, PLAYER)) {
                map[y][x] = ENEMY;
                iterator++;
            }
        }
    }

    private static boolean checkPlayerAround(int y, int x, char checkChar) {
        if (y - 1 >= 0 && map[y - 1][x] == checkChar) {
            return false;
        } else if (x - 1 >= 0 && map[y][x - 1] == checkChar) {
            return false;
        } else if (y + 1 < sizeMap && map[y + 1][x] == checkChar) {
            return false;
        } else if (x + 1 < sizeMap && map[y][x + 1] == checkChar) {
            return false;
        }
        return true;
    }

    private static void initEmptyMap() {
        map = new char[sizeMap][sizeMap];
        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                map[i][j] = EMPTY;
            }
        }
    }

    private static void putWalls() {
        int iterator = 0;
        while (iterator < countWalls) {
            int y = random.nextInt(sizeMap);
            int x = random.nextInt(sizeMap);
            if (map[y][x] == EMPTY) {
                map[y][x] = WALL;
                iterator++;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public static char getPLAYER() {
        return PLAYER;
    }

    public static char getEMPTY() {
        return EMPTY;
    }

    public static char getENEMY() {
        return ENEMY;
    }

    public static char getGOAL() {
        return GOAL;
    }

    public static char getWALL() {
        return WALL;
    }
}

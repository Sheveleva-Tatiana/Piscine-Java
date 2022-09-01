package com.example.tanks;

import com.dlsc.formsfx.model.structure.PasswordField;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Program extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    public static final double WIDTH = 1042;
    public static final double HEIGHT = 1042;
    private static BufferedReader in;
    public static PrintWriter out;
    private static final int SPEED = 10;
    public static String lastKey = "s";
    public static Canvas canvas;

    public static Character player;
    public static Character enemy;
    public static GraphicsContext gc;
    public static AnimationTimer animationTimer;
    public static Stage mainStage;
    public static boolean canPlay = false;
    public static boolean canConnect= false;



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        stage.setTitle("Tanks!");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKRED);

        Image background = new Image("field.png");

        getConnection();

        if (!in.ready()) {
            new AnimationTimer() {
                int i = 0;
                int wait = 0;
                @Override
                public void handle(long now) {
                    gc.drawImage(new Image("Wait" + i + ".png"), 0, 0);
                    if (wait < 15) {
                        wait++;
                    } else {
                        if (i < 3) {
                            i++;
                        } else {
                            i = 0;
                        }
                        wait = 0;
                    }
                    try {
                        if (in.ready())
                            this.stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        stage.show();

        player = new Character("playerup.png", 480, 850, root, 20, 980, gc);
        enemy = new Character("enemydown.png", 480, 72, root, 700, 20, gc);

        scene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (code.equals("SPACE") && lastKey.equals("SPACE")) {
                lastKey = code;
            } else {
                keys.put(event.getCode(), true);
                lastKey = code;
            }
        });
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    if (in.ready()) {
                        canPlay = true;
                    }
                    if (canPlay) {
                        updatePlayer();
                        updateEnemy();
                        updateBullet();
                        if (enemy.checkLeaveGame()) {
                            Program.GameOver("leftgame.png");
                        } else if (!enemy.checkLife())
                            Program.GameOver("win.png");
                        if (!player.checkLife())
                            Program.GameOver("lose.png");
                        gc.clearRect(0, 0, WIDTH, HEIGHT);
                        gc.drawImage(background, 0, 0);
                        player.render(gc);
                        enemy.render(gc);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();

    }

    private void updateBullet() throws IOException {
        if (keys.containsKey(KeyCode.SPACE)) {
            outShoot("/bulletUp.png", player, enemy, 0);
            keys.remove(KeyCode.SPACE);
            out.println("outshoot");
        }
    }

    public void updatePlayer() throws IOException {
        if (player.canMove("right") && (isPressed(KeyCode.D) || isPressed(KeyCode.RIGHT))) {
            player.moveRight(SPEED);
            out.println("right");
        } else if (player.canMove("left") && (isPressed(KeyCode.A) || isPressed(KeyCode.LEFT))) {
            player.moveLeft(SPEED);
            out.println("left");
        }
    }

    public void updateEnemy() throws IOException {
        if (in.ready()) {
            String read = in.readLine();
            if (enemy.canMove("left") && "right".contains(read)) {
                enemy.moveLeft(SPEED);
            } else if (enemy.canMove("right") && "left".contains(read)) {
                enemy.moveRight(SPEED);
            } else if ("outshoot".contains(read)) {
                outShoot("/bulletDown.png", enemy, player, enemy.getBoundary().getHeight());
            } else if("enemyLeftGame".contains(read)) {
                 enemy.kill();
            }
        }
    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void outShoot(String img, Character character, Character enemy, double forCorrect) {
        OutShoot outShoot = new OutShoot(character, img, gc, enemy, forCorrect);
        outShoot.start();
    }

    private void getConnection() {
        try {
            Socket clientSocket = new Socket("localhost", 8000);
            System.out.println("Connected to server");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Server closed");
            System.exit(1);
        }
    }



    public static void GameOver(String str) throws IOException {
        animationTimer.stop();
        Stage stage = new Stage();
        stage.setTitle("Tanks!");
        stage.setX(mainStage.getX());
        stage.setY(mainStage.getY());
        mainStage.close();

        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        out.println("gameOver");
        String getStr = in.readLine();
        while (!getStr.contains("stat")) {
            getStr = in.readLine();
        }
        String[] info = getStr.split(":");

        Image background = new Image(str);
        gc.drawImage(background, 0, 0);
        GraphicsContext gc2 = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc2.drawImage(new Image(str), 0, 0);

                gc2.strokeText( info[1], 535, 500 );
                gc2.strokeText( info[2], 535, 620 );
                gc2.strokeText( info[3], 535, 730 );

                gc2.strokeText( info[4], 780, 500 );
                gc2.strokeText( info[5], 780, 620 );
                gc2.strokeText( info[6], 780, 730 );

            }
        }.start();
        stage.show();
    }
}
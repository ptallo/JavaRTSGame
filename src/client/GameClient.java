package client;

import client.gui.StartScreen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameClient extends Application {
    private final int WIDTH = 512;
    private final int HEIGHT = 512;

    private Stage primaryStage;
    private Scene scene;
    private Socket socket;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

//        initSocketConnection();

        StartScreen start = new StartScreen(WIDTH, HEIGHT, this);
        root.getChildren().add(start);

        primaryStage.show();
    }

    private void initSocketConnection() {
        try {
            socket = new Socket("localhost", GameServer.PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Parent root) {
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
    }
}

package networking.client;

import gui.StartScreen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.server.GameServer;

import java.io.IOException;
import java.net.Socket;

public class GameClient extends Application {
    private final int WIDTH = 512;
    private final int HEIGHT = 512;

    private Stage primaryStage;
    private Scene scene;

    private ClientConnectionHandler handler;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        initSocketConnection();

        StartScreen start = new StartScreen(WIDTH, HEIGHT, this);
        root.getChildren().add(start);

        primaryStage.show();
    }

    private void initSocketConnection() {
        try {
            Socket socket = new Socket("localhost", GameServer.PORT);
            handler = new ClientConnectionHandler(socket);
            primaryStage.setOnCloseRequest(event -> {
                try {
                    handler.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Parent root) {
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
    }

    public ClientConnectionHandler getHandler() {
        return handler;
    }
}

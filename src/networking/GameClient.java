package networking;

import gui.MultiplayerScreen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameClient extends Application {

    private Stage primaryStage;
    private ConnectionHandler handler;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        int HEIGHT = 512;
        int WIDTH = 512;

        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        try {
            Socket socket = new Socket("localhost", GameServer.PORT);
            handler = new ConnectionHandler(socket) {
                @Override
                public void handleMessage(GameMessage message, ObjectOutputStream oos, ObjectInputStream ois, ConnectionHandler handler) throws IOException, ClassNotFoundException {
                    message.sendToServer(oos, ois);
                }
            };
            Runnable runnable = handler::listenForMessages;
            new Thread(runnable).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiplayerScreen screen = new MultiplayerScreen(WIDTH, HEIGHT, this);
        root.getChildren().add(screen);

        primaryStage.show();
    }

    public void setScene(Parent root) {
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
    }

    public ConnectionHandler getHandler() {
        return handler;
    }
}

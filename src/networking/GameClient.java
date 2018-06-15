package networking;

import core.GameLobby;
import gui.MultiplayerScreen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameClient extends Application {

    public static int HEIGHT = 512;
    public static int WIDTH = 512;

    private Stage primaryStage;
    private ClientConnectionHandler handler;

    private ObservableList<GameLobby> lobbyArrayList = FXCollections.observableList(new ArrayList<>());

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        try {
            Socket socket = new Socket("localhost", GameServer.PORT);
            handler = new ClientConnectionHandler(socket, this);
            Runnable runnable = handler::listenForMessages;
            new Thread(runnable).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiplayerScreen screen = new MultiplayerScreen(this);
        root.getChildren().add(screen);

        primaryStage.show();
    }

    public void setScene(Parent root) {
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
    }

    public ClientConnectionHandler getHandler() {
        return handler;
    }

    public ObservableList<GameLobby> getLobbyArrayList() {
        return lobbyArrayList;
    }
}

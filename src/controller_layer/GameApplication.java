package controller_layer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model_layer.Game;
import model_layer.Player;
import view_layer.GameScreen;

import java.util.ArrayList;

public class GameApplication extends Application {

    public static int HEIGHT = 512;
    public static int WIDTH = 512;

    private Stage primaryStage;
    private Player user = new Player();
    private Game game;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);

        initModel();

        GameScreen view = new GameScreen(game);
        root.getChildren().add(view);
        primaryStage.show();

        startGameLoop();
        view.drawGame(primaryStage.getWidth(), primaryStage.getHeight());
    }

    private void initModel() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(user);
        game = new Game(players);
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (game.getRunning()){
                if (!game.getPaused()){
                    game.update();
                }
            }
        }).start();
    }
}

package gui;

import core.Game;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import networking.GameClient;

public class GameScreen extends GridPane {

    private Game game;
    private GameClient client;

    public GameScreen(Game game, GameClient client) {
        this.game = game;
        this.client = client;

        setPrefSize(GameClient.WIDTH, GameClient.HEIGHT);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        Text label = new Text("GAME SCREEN");
        add(label, 0, 0);
    }
}

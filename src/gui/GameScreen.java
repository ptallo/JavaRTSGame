package gui;

import core.Game;
import core.Player;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import networking.GameClient;
import networking.MessageType;

import java.io.IOException;

public class GameScreen extends GridPane {

    private Game game;
    private GameClient client;
    private Boolean isConnected = true;

    private Text text;

    public GameScreen(Game game, GameClient client) {
        this.game = game;
        this.client = client;

        setPrefSize(GameClient.WIDTH, GameClient.HEIGHT);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        text = new Text("GAME SCREEN");
        add(text, 0, 0);

        client.getPlayer().setLoaded(true);
        // TODO add netty set player loaded
    }

    public void startGame(){
        Runnable drawLoop = () -> {
            while (isConnected) {
                text.setText("COUNT " + getGame().getCount());
                getGame().draw();
            }
        };
        new Thread(drawLoop).start();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

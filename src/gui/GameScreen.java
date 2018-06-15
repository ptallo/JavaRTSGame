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
        try {
            client.getHandler().sendMessage(MessageType.SET_PLAYER_LOADED, new Game(game), new Player(client.getPlayer()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        text.setText("GAME STARTED");
        Runnable drawLoop = () -> {
            while (isConnected) {
                game.draw();
            }
        };
        new Thread(drawLoop).start();
    }
}

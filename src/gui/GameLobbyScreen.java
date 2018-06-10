package gui;

import core.GameLobby;
import core.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import networking.GameClient;

public class GameLobbyScreen extends GridPane {

    private GameLobby lobby;
    private GameClient client;
    private Player owner;

    public GameLobbyScreen(GameLobby lobby, GameClient client, Player owner) {
        this.lobby = lobby;
        this.client = client;
        this.owner = owner;

        Label nameLabel = new Label("Name:" );
        Text name = new Text(lobby.getLobbyName());

        add(nameLabel, 0, 0);
        add(name, 1, 0);
    }
}

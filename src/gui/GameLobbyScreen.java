package gui;

import core.GameLobby;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import networking.client.GameClient;


public class GameLobbyScreen extends GridPane {

    private GameLobby lobby;

    public GameLobbyScreen(double width, double height, GameClient client, GameLobby lobby) {
        this.lobby = lobby;

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initRefreshButton(width, height, client);

        TableView tableView = new TableView();

        add(tableView, 0 ,1);
    }

    private void initRefreshButton(double width, double height, GameClient client) {
        Button refreshButton = new Button("Go Back");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            MultiplayerScreen screen = new MultiplayerScreen(width, height, client);
            client.setScene(screen);
        };
        refreshButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(refreshButton, 0, 0);
    }
}

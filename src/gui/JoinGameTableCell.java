package gui;

import core.GameLobby;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import networking.GameClient;
import networking.MessageType;

import java.io.IOException;


public class JoinGameTableCell extends TableCell<GameLobby, Boolean> {

    private Button joinGameButton;
    private GameClient client;

    public JoinGameTableCell(GameClient client) {
        this.client = client;
        initJoinGameButton();
    }

    private void initJoinGameButton() {
        joinGameButton = new Button("JOIN");
        joinGameButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            GameLobby lobby = (GameLobby) getTableRow().getItem();
            // TODO add netty join lobby
            lobby.addPlayer(client.getPlayer());
            GameLobbyScreen screen = new GameLobbyScreen(lobby, client);
            client.setScene(screen);
        };
        joinGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        if (!empty){
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(joinGameButton);
        } else {
            setGraphic(null);
        }
    }
}

package gui;

import core.GameLobby;
import core.Player;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import networking.client.GameClient;


public class ReadyPlayerTableCell extends TableCell<Player, Boolean> {

    private Player player;
    private GameLobby lobby;
    private GameClient client;

    private Button readyPlayerButton;

    public ReadyPlayerTableCell(Player player, GameLobby lobby, GameClient client) {
        this.player = player;
        this.lobby = lobby;
        this.client = client;
        initReadyPlayerButton();
    }

    private void initReadyPlayerButton() {
        readyPlayerButton = new Button("Ready");
        readyPlayerButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            player.setReady(!player.getReady());
            client.getHandler().updatePlayer(lobby, player);
        };
        readyPlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        if (!empty){
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(readyPlayerButton);
        } else {
            setGraphic(null);
        }
    }
}

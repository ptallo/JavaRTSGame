package gui;

import core.GameLobby;
import core.Player;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import networking.client.GameClient;


public class JoinGameTableCell extends TableCell<GameLobby, Boolean> {

    private Button joinGameButton;
    private GameClient client;
    private double width;
    private double height;
    private Player player;

    public JoinGameTableCell(double width, double height, GameClient client, Player player) {
        this.width = width;
        this.height = height;
        this.client = client;
        this.player = player;
        initJoinGameButton();
    }

    private void initJoinGameButton() {
        joinGameButton = new Button("JOIN");
        joinGameButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            GameLobby lobby = (GameLobby) getTableRow().getItem();
            GameLobby newLobby = client.getHandler().joinGameLobby(lobby, player);
            lobby.addPlayer(player);
            boolean inPlayers = false;
            if (newLobby != null) {
                for (Player lPlayer : newLobby.getPlayers()){
                    if (lPlayer.getId().equals(player.getId())){
                        inPlayers = true;
                    }
                }
            }
            if (inPlayers) {
                GameLobbyScreen lobbyScene = new GameLobbyScreen(width, height, client, lobby, player);
                client.setScene(lobbyScene);
            } else {
                // TODO implement popup telling them they have failed to join the game
            }
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

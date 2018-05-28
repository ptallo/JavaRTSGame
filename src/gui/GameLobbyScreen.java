package gui;

import core.GameLobby;
import core.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import networking.client.ClientConnectionHandler;
import networking.client.GameClient;

import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class GameLobbyScreen extends GridPane {

    private GameLobby lobby;
    private GameClient client;
    private Player player;

    private double width;
    private double height;

    public GameLobbyScreen(double width, double height, GameClient client, GameLobby lobby, Player player) {
        this.lobby = lobby;
        this.client = client;
        this.player = player;

        this.width = width;
        this.height = height;

        initConstraints();

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initRefreshButton(width, height, client);

        Label label = new Label(lobby.getLobbyName());
        add(label, 1, 0);

        TableView tableView = new TableView();
        add(tableView, 0, 1, 3, 3);

        Runnable update = new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(250);
                    updateGameLobby();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(update).start();
    }

    private void initRefreshButton(double width, double height, GameClient client) {
        Button refreshButton = new Button("Leave");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            MultiplayerScreen screen = new MultiplayerScreen(width, height, client);
            client.getHandler().leaveGameLobby(lobby, player);
            client.setScene(screen);
        };
        refreshButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(refreshButton, 0, 0);
    }

    private void initConstraints(){
        ColumnConstraints smallColumn = new ColumnConstraints();
        smallColumn.setPercentWidth(33);
        smallColumn.setHgrow(Priority.ALWAYS);

        getColumnConstraints().add(smallColumn);
        getColumnConstraints().add(smallColumn);
        getColumnConstraints().add(smallColumn);

        RowConstraints headerRow = new RowConstraints();
        headerRow.setPercentHeight(10);

        RowConstraints tableRow = new RowConstraints();
        tableRow.setPercentHeight(90);

        getRowConstraints().add(headerRow);
        getRowConstraints().add(tableRow);
    }

    private void updateGameLobby() {
        ClientConnectionHandler handler = client.getHandler();
        ArrayList<GameLobby> lobbies = handler.getGameLobbies();
        boolean inLobbies = false;
        for (GameLobby lobby : lobbies) {
            if (lobby.getId().equals(this.lobby.getId())){
                inLobbies = true;
                this.lobby = lobby;
            }
        }
        if (!inLobbies){
            MultiplayerScreen screen = new MultiplayerScreen(width, height, client);
            client.setScene(screen);

            Popup popup = new Popup();
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(new Text("The lobby has been closed! You have been kicked!"));
            popup.getContent().add(vBox);
            popup.show(client.getPrimaryStage());
        }
    }
}

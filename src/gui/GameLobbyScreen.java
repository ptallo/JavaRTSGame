package gui;

import core.GameLobby;
import core.Player;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    private TableView tableView;

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

        initLeaveGameButton();
        initStartGameButton();
        initTableSection();
        initHeaderSection();

        Runnable update = () -> {
            while (true) {
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

    private void initHeaderSection() {
        Text nameLabel = new Text("Name: ");
        add(nameLabel, 0, 0);

        Text name = new Text(lobby.getLobbyName());
        add(name, 1, 0);
    }

    private void initTableSection(){
        TableView tableView = new TableView();

        TableColumn<Player, String> nameColumn = new TableColumn<>("Player Id");
        TableColumn<Player, Boolean> readyColumn = new TableColumn<>("Player Id");

        tableView.getColumns().addAll(nameColumn, readyColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getId());
            }
        });

        readyColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Player, Boolean> param) {
                return new ReadOnlyBooleanWrapper(param.getValue().getReady());
            }
        });

        add(tableView, 0, 1, 3, 1);
        this.tableView = tableView;
    }

    private void initLeaveGameButton() {
        Button refreshButton = new Button("Leave");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            MultiplayerScreen screen = new MultiplayerScreen(width, height, client);
            client.getHandler().leaveGameLobby(lobby, player);
            client.setScene(screen);
        };
        refreshButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(refreshButton, 0, 2);
    }

    private void initStartGameButton(){
        Button startGameButton = new Button("Start");
        startGameButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            boolean startGame = true;
            for (Player player : lobby.getPlayers()) {
                if (!player.getReady()){
                    startGame = false;
                }
            }
            if (startGame) {
                GameScreen gameScreen = new GameScreen(width, height, client);
                client.setScene(gameScreen);
                // TODO start game on server
            }
        };
        startGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(startGameButton, 1, 2);
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
        tableRow.setPercentHeight(80);

        getRowConstraints().add(headerRow);
        getRowConstraints().add(tableRow);
        getRowConstraints().add(headerRow);
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lobby Dialog");
            alert.setContentText("You have been kicked from the lobby!");
            alert.showAndWait();
        }
        ObservableList<Player> data = FXCollections.observableArrayList(lobby.getPlayers());
        tableView.setItems(data);
    }
}

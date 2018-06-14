package gui;

import core.GameLobby;
import core.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import networking.GameClient;
import networking.MessageType;

import java.io.IOException;

import static java.lang.Thread.sleep;


public class GameLobbyScreen extends GridPane {

    private GameLobby lobby;
    private Player player;
    private GameClient client;
    private double width;
    private double height;

    private TableView tableView;
    private boolean onScreen;

    public GameLobbyScreen(GameLobby lobby, GameClient client, Player player, double width, double height) {
        this.lobby = lobby;
        this.client = client;
        this.player = player;
        this.width = width;
        this.height = height;
        this.onScreen = true;

        setConstraints(10, 3);
        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initHeaderSection();
        initLeaveButton();

        initStartGameButton();
        initCheckBox();
        initTableView();

        client.getLobbyArrayList().addListener(new ListChangeListener<GameLobby>() {
            @Override
            public void onChanged(Change<? extends GameLobby> c) {
                for (GameLobby newLobby : c.getList()){
                    if (newLobby.getId().equals(lobby.getId())){
                        tableView.setItems(FXCollections.observableList(newLobby.getPlayers()));
                        setLobby(newLobby);
                    }
                }
            }
        });

        Runnable runnable = this::updateGameLobbyList;
        new Thread(runnable).start();
    }

    private void setConstraints(int headerHeight, int numColumns) {
        ColumnConstraints smallColumn = new ColumnConstraints();
        smallColumn.setPercentWidth(100 / numColumns);
        smallColumn.setHgrow(Priority.ALWAYS);

        for (int i = 0; i < numColumns; i++) {
            getColumnConstraints().add(smallColumn);
        }

        RowConstraints headFootRow = new RowConstraints();
        headFootRow.setPercentHeight(headerHeight);

        RowConstraints tableRow = new RowConstraints();
        tableRow.setPercentHeight(100 - (headerHeight) * 2);

        getRowConstraints().add(headFootRow);
        getRowConstraints().add(tableRow);
        getRowConstraints().add(headFootRow);
    }

    private void initHeaderSection() {
        Label nameLabel = new Label("Name:");
        Text name = new Text(lobby.getLobbyName());

        add(nameLabel, 0, 0);
        add(name, 1, 0);
    }

    private void initLeaveButton() {
        Button initLeaveButton = new Button("Leave");
        initLeaveButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO add leave game lobby functionality
            }
        };
        initLeaveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        add(initLeaveButton, 0, 2);
    }

    private void initStartGameButton() {
        Button initStartButton = new Button("Start Game");
        initStartButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO add start game functionality
            }
        };
        initStartButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        add(initStartButton, 1, 2);
    }

    private void initCheckBox() {
        CheckBox checkBox = new CheckBox("Ready");
        checkBox.setAllowIndeterminate(false);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                player.setReady(newValue);
                Player player = new Player(this.player);
                client.getHandler().sendMessage(MessageType.SET_PLAYER_READY, lobby, player);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(checkBox, 2, 2);
    }

    private void initTableView() {
        TableView tableView = new TableView();

        TableColumn<Player, String> nameColumn = new TableColumn<>("Player Name");
        TableColumn<Player, Boolean> readyColumn = new TableColumn<>("Ready");

        tableView.getColumns().addAll(nameColumn, readyColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getId()));
        readyColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getReady()));

        add(tableView, 0, 1, 3, 1);
        this.tableView = tableView;
    }

    private void updateGameLobbyList() {
        while (onScreen) {
            try {
                sleep(100);
                client.getHandler().sendMessage(MessageType.GET_LOBBIES, null);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLobby(GameLobby lobby) {
        this.lobby = lobby;
    }
}

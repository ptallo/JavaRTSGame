package gui;

import core.GameLobby;
import core.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import networking.GameClient;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class MultiplayerScreen extends GridPane {

    private GameClient client;
    private double width;
    private double height;
    private TableView tableView;
    private Player player;

    public MultiplayerScreen(double width, double height, GameClient client) {
        this.client = client;
        this.width = width;
        this.height = height;

        player = new Player();

        setConstraints(10, 33);
        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initCreateGameLobbyButton();
        initRefreshButton();
        initGameTableView();

        client.getLobbyArrayList().addListener((ListChangeListener<GameLobby>) c -> tableView.setItems(client.getLobbyArrayList()));

        populateTable();
    }

    private void setConstraints(int headRowHeight, int tableColumnWidth) {
        ColumnConstraints smallColumn = new ColumnConstraints();
        smallColumn.setPercentWidth(tableColumnWidth);
        smallColumn.setHgrow(Priority.ALWAYS);

        getColumnConstraints().add(smallColumn);
        getColumnConstraints().add(smallColumn);
        getColumnConstraints().add(smallColumn);

        RowConstraints headerRow = new RowConstraints();
        headerRow.setPercentHeight(headRowHeight);

        RowConstraints tableRow = new RowConstraints();
        tableRow.setPercentHeight(100 - headRowHeight);

        getRowConstraints().add(headerRow);
        getRowConstraints().add(tableRow);
    }

    private void initCreateGameLobbyButton() {
        Button createGameLobbyButton = new Button("Create Game");
        createGameLobbyButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = event -> {
            CreateLobbyPopup lobbyPopup = new CreateLobbyPopup(player);
            lobbyPopup.setOnHidden(hideEvent -> {
                GameLobby lobby = lobbyPopup.getGameLobby();
                if (lobby != null) {
                    GameLobbyScreen lobbyScreen = new GameLobbyScreen(lobby, client, player, width, height);
                    client.setScene(lobbyScreen);
                    try {
                        client.getHandler().sendMessage(2, lobby);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            lobbyPopup.show();
        };
        createGameLobbyButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(createGameLobbyButton, 0, 0);
    }

    private void initRefreshButton(){
        Button refreshButton = new Button("Refresh");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> handler = event -> populateTable();
        refreshButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        add(refreshButton, 1, 0);
    }

    private void initGameTableView() {
        TableView tableView = new TableView();

        TableColumn<GameLobby, String> nameColumn = new TableColumn<GameLobby, String>("Game Name");
        TableColumn<GameLobby, String> idColumn = new TableColumn<GameLobby, String>("Game ID");
        TableColumn<GameLobby, Boolean> joinGameButtonColumn = new TableColumn<GameLobby, Boolean>("");

        tableView.getColumns().addAll(nameColumn, idColumn, joinGameButtonColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getLobbyName()));

        idColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getId()));

        joinGameButtonColumn.setCellValueFactory(param -> null);

        joinGameButtonColumn.setCellFactory(param -> new JoinGameTableCell(width, height, client, player));

        add(tableView, 0, 1, 3, 3);
        this.tableView = tableView;
    }

    private void populateTable() {
        try {
            client.getHandler().sendMessage(1, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

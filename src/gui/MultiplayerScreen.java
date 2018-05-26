package gui;

import core.GameLobby;
import core.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import networking.client.ClientConnectionHandler;
import networking.client.GameClient;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;


public class MultiplayerScreen extends GridPane {

    private GameClient client;
    private double width;
    private double height;
    private TableView tableView;
    private TextField searchGameTextField;
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
        initBackButton();
        initGameTableView();

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

    private void initBackButton() {
        Button backButton = new Button("Back");
        backButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new StartScreen(getWidth(), getHeight(), client));
            }
        };
        backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(backButton, 0, 0);
    }

    private void initRefreshButton() {
        Button refreshButton = new Button("Refresh");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tableView.getItems().removeAll(tableView.getItems());
                populateTable();
            }
        };
        refreshButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(refreshButton, 2, 0);
    }

    private void initCreateGameLobbyButton() {
        Button createGameLobbyButton = new Button("Create Game");
        createGameLobbyButton.setMaxWidth(Double.MAX_VALUE);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CreateLobbyPopup lobbyPopup = new CreateLobbyPopup(player);
                lobbyPopup.setOnHidden(hideEvent -> {
                    GameLobby lobby = lobbyPopup.getGameLobby();
                    if (lobby != null) {
                        ClientConnectionHandler handler = client.getHandler();
                        handler.createGameLobby(lobby);
                    }
                    GameLobbyScreen lobbyScreen = new GameLobbyScreen(width, height, client, lobby);
                    client.setScene(lobbyScreen);
                });
                lobbyPopup.show();
            }
        };
        createGameLobbyButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(createGameLobbyButton, 1, 0);
    }

    private void initGameTableView() {
        TableView tableView = new TableView();

        TableColumn<GameLobby, String> nameColumn = new TableColumn<GameLobby, String>("Game Name");
        TableColumn<GameLobby, String> idColumn = new TableColumn<GameLobby, String>("Game ID");

        tableView.getColumns().addAll(nameColumn, idColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GameLobby, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GameLobby, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getLobbyName());
            }
        });

        idColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GameLobby, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GameLobby, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getId());
            }
        });

        add(tableView, 0, 1, 3, 3);
        this.tableView = tableView;
    }

    private void populateTable() {
        ArrayList<GameLobby> lobbyArrayList = client.getHandler().getGameLobbies();
        ObservableList<GameLobby> data = FXCollections.observableArrayList(lobbyArrayList);
        tableView.setItems(data);
    }
}

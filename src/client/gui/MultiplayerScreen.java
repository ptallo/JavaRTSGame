package client.gui;

import client.GameClient;
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


public class MultiplayerScreen extends GridPane {

    private GameClient client;
    private double width;
    private double height;

    public MultiplayerScreen(double width, double height, GameClient client) {
        this.client = client;
        this.width = width;
        this.height = height;

        setConstraints(10, 60);

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initSearchTextField();
        initRefreshButton();
        initBackButton();
        initGameTableView();
    }

    private void setConstraints(int headRowHeight, int tableColumnWidth) {
        ColumnConstraints smallColumn = new ColumnConstraints();
        smallColumn.setPercentWidth((100 - tableColumnWidth) / 2);
        smallColumn.setHgrow(Priority.ALWAYS);

        ColumnConstraints largeColumn = new ColumnConstraints();
        largeColumn.setPercentWidth(tableColumnWidth);

        getColumnConstraints().add(smallColumn);
        getColumnConstraints().add(largeColumn);
        getColumnConstraints().add(smallColumn);

        RowConstraints headerRow = new RowConstraints();
        headerRow.setPercentHeight(headRowHeight);

        RowConstraints tableRow = new RowConstraints();
        tableRow.setPercentHeight(100 - headRowHeight);

        getRowConstraints().add(headerRow);
        getRowConstraints().add(tableRow);
    }

    public void initBackButton(){
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

    private void initSearchTextField() {
        TextField searchGameTextField = new TextField();
        add(searchGameTextField, 1, 0);
    }

    private void initRefreshButton() {
        Button refreshButton = new Button("Refresh");
        refreshButton.setMaxWidth(Double.MAX_VALUE);
        add(refreshButton, 2, 0);
    }

    public void initGameTableView(){
        TableView tableView = new TableView();

        TableColumn nameColumn = new TableColumn("Game Name");
        TableColumn idColumn = new TableColumn("Game ID");

        tableView.getColumns().addAll(nameColumn, idColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        add(tableView, 0, 1, 3, 3);
    }
}

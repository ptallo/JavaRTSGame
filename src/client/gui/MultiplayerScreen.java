package client.gui;

import client.GameClient;
import com.sun.tools.javadoc.Start;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class MultiplayerScreen extends GridPane {

    private GameClient client;
    private int width;
    private int height;

    public MultiplayerScreen(int width, int height, GameClient client) {
        this.client = client;
        this.width = width;
        this.height = height;

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        initSeachTextField();
        initRefreshButton();
        initBackButton();
    }

    private void initRefreshButton() {
        Button refreshButton = new Button("Refresh");
        add(refreshButton, 2, 0);
    }

    private void initSeachTextField() {
        TextField searchGameTextField = new TextField();
        add(searchGameTextField, 1, 0);
    }

    public void initBackButton(){
        Button backButton = new Button("Back");
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new StartScreen(width, height, client));
            }
        };
        backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        add(backButton, 0, 0);
    }
}

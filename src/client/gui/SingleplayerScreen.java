package client.gui;

import client.GameClient;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SingleplayerScreen extends GridPane {

    private int height;
    private int width;
    private GameClient client;

    public SingleplayerScreen(int height, int width, GameClient client){
        this.height = height;
        this.width = width;
        this.client = client;

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        Text text = new Text("SINGLEPLAYER MENU");
        add(text, 1, 0);
        initBackButton();
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

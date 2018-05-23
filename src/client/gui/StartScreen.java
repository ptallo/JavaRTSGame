package client.gui;

import client.GameClient;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class StartScreen extends VBox {


    private final GameClient client;

    public StartScreen(int width, int height, GameClient client){
        this.client = client;
        setAlignment(Pos.CENTER);
        setPrefSize(width, height);
        setSpacing(20);

        initSinglePlayerButton(width, height, client);

        initMultiplayerButton(width, height, client);
    }

    private void initMultiplayerButton(int width, int height, GameClient client) {
        Button multiplayer = new Button("Multiplayer");
        EventHandler<MouseEvent> multiplayerEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new MultiplayerScreen(width, height, client));
            }
        };
        multiplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, multiplayerEventHandler);
        getChildren().add(multiplayer);
    }

    private void initSinglePlayerButton(int width, int height, GameClient client) {
        Button singleplayer = new Button("Singleplayer");
        EventHandler<MouseEvent> singleplayerEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new SingleplayerScreen(width, height, client));
            }
        };
        singleplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, singleplayerEventHandler);
        getChildren().add(singleplayer);
    }
}

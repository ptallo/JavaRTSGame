package gui;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import networking.client.GameClient;


public class StartScreen extends VBox {

    private final GameClient client;

    public StartScreen(double width, double height, GameClient client){
        this.client = client;
        setAlignment(Pos.CENTER);
        setPrefSize(width, height);
        setSpacing(20);

        initSinglePlayerButton(width, height, client);

        initMultiplayerButton(width, height, client);
    }

    private void initMultiplayerButton(double width, double height, GameClient client) {
        Button multiPlayerButton = new Button("Multiplayer");
        multiPlayerButton.setMinWidth(getPrefWidth() * 0.8);
        EventHandler<MouseEvent> multiplayerEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new MultiplayerScreen(width, height, client));
            }
        };
        multiPlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, multiplayerEventHandler);
        getChildren().add(multiPlayerButton);
    }

    private void initSinglePlayerButton(double width, double height, GameClient client) {
        Button singlePlayerButton = new Button("Singleplayer");
        singlePlayerButton.setMinWidth(getPrefWidth() * 0.8);
        EventHandler<MouseEvent> singleplayerEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.setScene(new SingleplayerScreen(width, height, client));
            }
        };
        singlePlayerButton.addEventFilter(MouseEvent.MOUSE_CLICKED, singleplayerEventHandler);
        getChildren().add(singlePlayerButton);
    }
}

package gui;

import core.GameLobby;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networking.GameClient;

public class CreateLobbyPopup extends Stage {

    public GameLobby lobby;
    private GridPane pane;
    private TextField nameTextField;
    private GameClient client;

    public CreateLobbyPopup(GameClient client){
        this.client = client;
        pane = new GridPane();

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        initTextField();

        initCreateGameButton();

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    private void initTextField() {
        Text label = new Text("Game Name:");
        pane.add(label, 0, 0);

        nameTextField = new TextField();
        pane.add(nameTextField, 1, 0);
    }

    private void initCreateGameButton() {
        Button createGameButton = new Button("Create Game");
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String name = nameTextField.getText();
                if (!name.isEmpty()){
                    lobby = new GameLobby(client.getPlayer(),name, 2);
                }
                close();
            }
        };
        createGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        pane.add(createGameButton, 0, 1);
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public GameLobby getGameLobby(){
        return lobby;
    }
}

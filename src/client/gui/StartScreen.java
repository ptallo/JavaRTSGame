package client.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class StartScreen extends VBox {

    public StartScreen(int width, int height){
        ObservableList<Node> children = getChildren();
        setAlignment(Pos.CENTER);
        setPrefSize(width, height);
        setSpacing(20);

        Button singleplayer = new Button("Singleplayer");
        children.add(singleplayer);

        Button multiplayer = new Button("Multiplayer");
        children.add(multiplayer);
    }
}

package gui;

import core.Game;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameScreen extends GridPane {

    public GameScreen(double width, double height, Game game) {
        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        Text label = new Text("GAME SCREEN");
        add(label, 0, 0);
    }
}

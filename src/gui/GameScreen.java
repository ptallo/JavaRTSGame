package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameScreen extends GridPane {

    private Text text;

    public GameScreen(int width, int height) {

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        text = new Text("GAME SCREEN");
        add(text, 0, 0);

    }

    public void startGame() {
        Runnable drawLoop = () -> {

        };
        new Thread(drawLoop).start();
    }
}

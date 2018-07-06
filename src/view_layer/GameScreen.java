package view_layer;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model_layer.Game;

public class GameScreen extends GridPane {

    private Game game;
    private Text text;

    public GameScreen(int width, int height, Game game) {
        this.game = game;

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        text = new Text("GAME SCREEN");
        add(text, 0, 0);
    }

    public void drawGame() {
        new Thread(() -> {
            game.draw();
        }).start();
    }
}

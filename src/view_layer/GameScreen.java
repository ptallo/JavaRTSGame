package view_layer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model_layer.Game;

public class GameScreen extends GridPane {

    private Game game;
    private Text text;

    private GraphicsContext gc;
    private Canvas canvas;

    private Integer width;
    private Integer height;

    public GameScreen(int width, int height, Game game) {
        this.width = width;
        this.height = height;
        this.game = game;

        setPrefSize(width, height);
        setHgap(10);
        setVgap(10);

        canvas = new Canvas(width, height * 0.8);
        add(canvas, 0, 0);

        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 48));
    }

    public void drawGame() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, width, height);

                gc.setFill(Color.RED);
                gc.fillText(game.getCount().toString(), 50, 50);
            }
        }.start();
    }
}

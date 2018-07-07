package view_layer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.InputItem;

public class GameScreen extends VBox {

    private Game game;

    private Canvas canvas;
    private GraphicsContext gc;
    private double canvasPercentage = 0.7;

    public GameScreen(Game game) {
        this.game = game;

        initEventHandlers();
    }

    public void drawGame(double width, double height) {
        initCanvas(width, height);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game.getRunning() && !game.getPaused()){
                    gc.setFill(Color.LIGHTGRAY);
                    gc.fillRect(0, 0, width, height);

                    game.draw(gc);
                }
            }
        }.start();
    }

    private void initEventHandlers() {
        setOnMouseClicked(event -> {
            InputItem item = new InputItem(event);
        });

        setOnKeyPressed(event -> {
            InputItem item = new InputItem(event);
        });
    }

    private void initCanvas(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    private void initGUI(){
        HBox guiHBox = new HBox();
    }
}

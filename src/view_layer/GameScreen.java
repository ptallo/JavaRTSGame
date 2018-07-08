package view_layer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.InputItem;
import model_layer.Player;

public class GameScreen extends VBox {

    private Game game;
    private Player user;

    private Canvas canvas;
    private GraphicsContext gc;

    public GameScreen(Game game, Player user) {
        this.game = game;
        this.user = user;

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
        setOnMousePressed(event -> {
            InputItem item = new InputItem(event);
            if (user != null) {
                user.addInput(item);
            }
        });

        setOnMouseReleased(event -> {
            InputItem item = new InputItem(event);
            if (user != null) {
                user.addInput(item);
            }
        });

        setOnMouseMoved(event -> {
            InputItem item = new InputItem(event);
            if (user != null) {
                user.addInput(item);
            }
        });

        setOnKeyPressed(event -> {
            InputItem item = new InputItem(event);
            if (user != null) {
                user.addInput(item);
            }
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

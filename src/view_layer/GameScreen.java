package view_layer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model_layer.Game;
import model_layer.InputItem;
import model_layer.Player;

public class GameScreen extends VBox {

    private Game game;
    private Player user;

    private Canvas canvas;
    private GraphicsContext gc;

    private Double width;
    private Double height;

    private HBox guiHBox;

    private ActionPane actionPane;
    private SelectedUnitsPane selectedUnitsPane;
    private MinimapPane minimapPane;

    public GameScreen(Game game, Player user) {
        this.game = game;
        this.user = user;
    }

    public void drawGame(double width, double height) {
        initCanvas(width, height);
        updateDimensions(width, height);
        initGUI();
        initEventHandlers();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game.getRunning() && !game.getPaused()){
                    game.draw(gc);
                    actionPane.update(game, user);
                    selectedUnitsPane.update(game, user);
                    minimapPane.update(game, user);
                }
            }
        }.start();
    }

    private void updateDimensions(double width, double height) {
        this.width = width;
        this.height = height;
        user.updateDimensions(width, height);
    }

    private void initEventHandlers() {
        canvas.setOnMousePressed(event -> {
            InputItem item = new InputItem(event, canvas);
            if (user != null) {
                user.addInput(item);
            }
        });

        canvas.setOnMouseReleased(event -> {
            InputItem item = new InputItem(event, canvas);
            if (user != null) {
                user.addInput(item);
            }
        });

        canvas.setOnMouseDragged(event -> {
            InputItem item = new InputItem(event, canvas);
            if (user != null) {
                user.addInput(item);
            }
        });

        canvas.setOnKeyPressed(event -> {
            InputItem item = new InputItem(event, canvas);
            if (user != null) {
                user.addInput(item);
            }
        });

        canvas.setOnMouseMoved(event -> {
            InputItem item = new InputItem(event, canvas);
            if (user != null) {
                user.addInput(item);
            }
        });

        guiHBox.setOnMouseMoved(event -> {
            InputItem item = new InputItem(event, guiHBox);
            if (user != null) {
                user.addInput(item);
            }
        });
    }

    private void initCanvas(double width, double height) {
        canvas = new Canvas(width, height * 0.7);
        getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    private void initGUI(){
        guiHBox = new HBox();
        guiHBox.setFillHeight(true);
        guiHBox.setSpacing(width * 0.0125);

        actionPane = new ActionPane(width, height);
        selectedUnitsPane = new SelectedUnitsPane(width, height);
        minimapPane = new MinimapPane(width, height);
        guiHBox.getChildren().addAll(actionPane, selectedUnitsPane, minimapPane);
        getChildren().add(guiHBox);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

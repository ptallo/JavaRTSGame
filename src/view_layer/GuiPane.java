package view_layer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.Player;

public abstract class GuiPane extends GridPane {

    private boolean initialized = false;
    protected Canvas canvas;
    protected GraphicsContext gc;

    public GuiPane(Double width, Double height) {
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-border-color: #050101");
        setMinWidth(width * 0.975 / 3);
        setMinHeight(height * 0.3);

        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        addRow();
        addColumn();
        add(canvas, 0, 0);

        initEventHandlers();
    }

    protected abstract void populateUI(Game game, Player user);
    protected abstract void initEventHandlers();

    public void update(Game game, Player user) {
        setMargin(canvas, new Insets(0, 0, 0, 0));
        resetUI();
        setupUI();
        populateUI(game, user);
    }

    private void resetUI() {
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    protected void setupUI() {
        if (!initialized || (canvas.getWidth() == 0 && canvas.getHeight() == 0)) {
            initialized = true;
            canvas.setWidth(getWidth());
            canvas.setHeight(getHeight());
        }
    }

    private void addRow() {
        RowConstraints rowConstraints = new RowConstraints();
        getRowConstraints().add(rowConstraints);

        for (RowConstraints constraints : getRowConstraints()) {
            constraints.setPercentHeight(getRowConstraints().size() > 0 ? 100 : 100 / getRowConstraints().size());
        }
    }

    private void addColumn() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        getColumnConstraints().add(columnConstraints);

        for (ColumnConstraints constraints : getColumnConstraints()) {
            constraints.setPercentWidth(getColumnConstraints().size() > 0 ? 100 : 100 / getRowConstraints().size());
        }
    }
}

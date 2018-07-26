package view_layer;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model_layer.Game;
import model_layer.Player;

public abstract class GuiPane extends GridPane {

    public GuiPane(Double width, Double height) {
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-border-color: #050101");
        setMinWidth(width * 0.975 / 3);
        setMinHeight(height * 0.3);
    }

    abstract void update(Game game, Player use);

    protected void addRow() {
        RowConstraints rowConstraints = new RowConstraints();
        getRowConstraints().add(rowConstraints);

        for (RowConstraints constraints : getRowConstraints()) {
            constraints.setPercentHeight(getRowConstraints().size() > 0 ? 100 : 100 / getRowConstraints().size());
        }
    }

    protected void addColumn() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        getColumnConstraints().add(columnConstraints);

        for (ColumnConstraints constraints : getColumnConstraints()) {
            constraints.setPercentWidth(getColumnConstraints().size() > 0 ? 100 : 100 / getRowConstraints().size());
        }
    }
}

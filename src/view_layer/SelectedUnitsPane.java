package view_layer;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.Player;
import model_layer.components.graphics.RenderComponent;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;

public class SelectedUnitsPane extends GuiPane {

    private int maxItemsPerRow = 20;
    private int maxItemsPerColumn = 5;

    private Canvas canvas;
    private GraphicsContext gc;

    public SelectedUnitsPane(Double width, Double height) {
        super(width, height);
        canvas = new Canvas(getWidth(), getHeight());
        gc = canvas.getGraphicsContext2D();
        addRow();
        addColumn();
        add(canvas, 0, 0);
    }

    @Override
    protected void populateUI(Game game, Player user) {
        ArrayList<ObjectInterface> selectedObjects = game.getPlayerToSelectedObjectMap().get(user);
        if (selectedObjects != null) {
            for (int i = 0; i < selectedObjects.size(); i++) {
                RenderComponent renderComponent = selectedObjects.get(i).getRenderComponent();
                gc.drawImage(
                        renderComponent.getImage(),
                        0,
                        0,
                        renderComponent.getFrameWidth(),
                        renderComponent.getFrameHeight(),
                        i % maxItemsPerRow * renderComponent.getFrameWidth(),
                        i / maxItemsPerRow * renderComponent.getFrameHeight(),
                        renderComponent.getFrameWidth(),
                        renderComponent.getFrameHeight()
                );
            }
        }
    }

    @Override
    protected void setupUI() {
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
    }

    @Override
    protected void resetUI() {
        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}

package view_layer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.Player;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectedUnitsPane extends GuiPane {

    private boolean initialized = false;
    private int maxItemsPerRow = 20;
    private int maxItemsPerColumn = 5;

    private Canvas canvas;
    private GraphicsContext gc;

    private HashMap<ObjectInterface, CanvasItem> canvasItems = new HashMap<>();

    public SelectedUnitsPane(Double width, Double height) {
        super(width, height);
        canvas = new Canvas();
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
                CanvasItem item = canvasItems.get(selectedObjects.get(i));
                RenderComponent renderComponent = selectedObjects.get(i).getRenderComponent();
                Rectangle drawRect = new Rectangle(
                        i % maxItemsPerRow * renderComponent.getFrameWidth(),
                        i / maxItemsPerRow * renderComponent.getFrameHeight(),
                        renderComponent.getFrameWidth(),
                        renderComponent.getFrameHeight()
                );
                Rectangle sourceRect = new Rectangle(0, 0, renderComponent.getFrameWidth(), renderComponent.getFrameHeight());

                if (item == null) {
                    item = new CanvasItem(renderComponent.getImage(), drawRect, sourceRect);
                } else {
                    item.setDrawPoint(new Point(drawRect.getX(), drawRect.getY()));
                }

                canvasItems.put(selectedObjects.get(i), item);
                item.draw(gc);
            }
        }
    }

    @Override
    protected void setupUI() {
        if (!initialized || (canvas.getWidth() == 0 && canvas.getHeight() == 0)) {
            canvas.setWidth(getWidth());
            canvas.setHeight(getHeight());
            initialized = true;
        }
    }

    @Override
    protected void resetUI() {
        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    protected void initEventHandlers() {
        canvas.setOnMousePressed(event -> {

        });

        canvas.setOnKeyPressed(event -> {

        });
    }
}

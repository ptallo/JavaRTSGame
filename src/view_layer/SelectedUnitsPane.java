package view_layer;

import javafx.scene.input.MouseEvent;
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

    private int maxItemsPerRow = 20;
    private int maxItemsPerColumn = 5;

    private HashMap<ObjectInterface, CanvasItem> canvasItems = new HashMap<>();

    public SelectedUnitsPane(Double width, Double height) {
        super(width, height);
    }

    @Override
    protected void populateUI(Game game, Player user) {
        ArrayList<ObjectInterface> selectedObjects = game.getPlayerToSelectedObjectMap().get(user);
        if (selectedObjects != null) {
            for (int i = 0; i < selectedObjects.size(); i++) {
                CanvasItem item = canvasItems.get(selectedObjects.get(i));
                ObjectInterface objectInterface = selectedObjects.get(i);
                RenderComponent renderComponent = objectInterface.getRenderComponent();
                Rectangle drawRect = new Rectangle(
                        i % maxItemsPerRow * renderComponent.getFrameWidth(),
                        i / maxItemsPerRow * renderComponent.getFrameHeight(),
                        renderComponent.getFrameWidth(),
                        renderComponent.getFrameHeight()
                );
                Rectangle sourceRect = new Rectangle(0, 0, renderComponent.getFrameWidth(), renderComponent.getFrameHeight());

                if (item == null) {
                    item = new CanvasItem(renderComponent.getImage(), drawRect, sourceRect) {
                        @Override
                        void activate(MouseEvent event) {
                            if (event.isControlDown()){
                                game.getPlayerToSelectedObjectMap().get(user).remove(objectInterface);
                            }
                        }
                    };
                } else {
                    item.setDrawPoint(new Point(drawRect.getX(), drawRect.getY()));
                }

                canvasItems.put(selectedObjects.get(i), item);
                item.draw(gc);
            }
        }
    }

    @Override
    protected void initEventHandlers() {
        canvas.setOnMousePressed(event -> {
            for (CanvasItem item : canvasItems.values()) {
                item.handleMouseEvent(event);
            }
        });
    }
}

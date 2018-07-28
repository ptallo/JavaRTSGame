package view_layer;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model_layer.Game;
import model_layer.Player;
import model_layer.components.graphics.RenderComponent;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;

public class SelectedUnitsPane extends GuiPane {

    private final int maxItemsPerRow = 20;
    private final int maxItemsPerColumn = 5;

    public SelectedUnitsPane(Double width, Double height) {
        super(width, height);
    }

    @Override
    protected void populateUI(Game game, Player user) {
        ArrayList<ObjectInterface> selectedObjects = game.getPlayerToSelectedObjectMap().get(user);
        if (selectedObjects != null) {
            for (int i = 0; i < selectedObjects.size(); i++) {
                RenderComponent renderComponent = selectedObjects.get(i).getRenderComponent();
                ImageView imageView = new ImageView(renderComponent.getImage());
                imageView.setViewport(new Rectangle2D(0, 0, renderComponent.getFrameWidth(), renderComponent.getFrameHeight()));
                if (i <= maxItemsPerColumn * maxItemsPerRow) {
                    add(imageView, i % maxItemsPerRow, i / maxItemsPerRow);
                }
            }
        }
    }

    @Override
    protected void setupUI() {
        for (int i = 0; i < maxItemsPerRow; i++) {
            addColumn();
        }
        for (int i = 0; i < maxItemsPerColumn; i++) {
            addRow();
        }
    }

    @Override
    protected void resetUI() {
        getChildren().removeAll(getChildren());
        getRowConstraints().removeAll(getRowConstraints());
        getColumnConstraints().removeAll(getColumnConstraints());
    }
}

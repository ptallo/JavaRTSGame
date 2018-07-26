package view_layer;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model_layer.Game;
import model_layer.Player;
import model_layer.components.graphics.RenderComponent;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;

public class SelectedUnitsPane extends GuiPane {

    private final int maxItemsPerRow = 10;

    public SelectedUnitsPane(Double width, Double height) {
        super(width, height);
    }

    @Override
    public void update(Game game, Player user) {
        resetUI();
        populateColumns();
        ArrayList<ObjectInterface> selectedObjects = game.getPlayerToSelectedObjectMap().get(user);
        if (selectedObjects != null) {
            for (int i = 0; i < selectedObjects.size(); i++) {
                if (i % maxItemsPerRow == 0) {
                    addRow();
                }
                RenderComponent renderComponent = selectedObjects.get(i).getRenderComponent();
                ImageView imageView = new ImageView(renderComponent.getImage());
                imageView.setViewport(new Rectangle2D(0, 0, renderComponent.getFrameWidth(), renderComponent.getFrameHeight()));
                add(imageView, i % maxItemsPerRow, i / maxItemsPerRow);
            }
        }
    }

    private void populateColumns() {
        for (int i = 0; i < maxItemsPerRow; i++) {
            addColumn();
        }
    }

    private void resetUI() {
        getChildren().removeAll(getChildren());
        getRowConstraints().removeAll(getRowConstraints());
        getColumnConstraints().removeAll(getColumnConstraints());
    }
}

package view_layer;

import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.Player;
import model_layer.object_interface.map.MapTile;

public class MinimapPane extends GuiPane {

    public MinimapPane(Double width, Double height) {
        super(width, height);
    }

    @Override
    protected void initEventHandlers() {

    }

    @Override
    protected void populateUI(Game game, Player user) {
        // determine needed scale
        
    }
}

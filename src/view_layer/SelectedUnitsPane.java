package view_layer;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model_layer.Game;
import model_layer.Player;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;

public class SelectedUnitsPane extends GridPane implements GuiPane {

    public SelectedUnitsPane() {
        
    }

    @Override
    public void update(Game game, Player user) {
        add(new Text("HERE"), 0, 0);
        if (game != null && user != null) {
            ArrayList<ObjectInterface> selectedObjects = game.getPlayerToSelectedObjectMap().get(user);
            if (selectedObjects != null) {
                add(new Text(String.valueOf(selectedObjects.size())), 0, 0);
                System.out.println(selectedObjects.size());
            }
        }
    }
}

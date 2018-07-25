package view_layer;

import model_layer.Game;
import model_layer.Player;

public interface GuiPane {
    void update(Game game, Player use);
}

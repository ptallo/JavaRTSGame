package controller_layer;

import javafx.collections.ListChangeListener;
import model_layer.Game;
import model_layer.InputItem;
import model_layer.Player;
import view_layer.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private GameScreen view;
    private Game game;
    private Player user;

    public GameController(Player user) {
        this.user = user;

        initModel();
        view = new GameScreen(game, user);
        initInputHandling();
    }

    private void initInputHandling() {
        for (Player player : game.getPlayers()){
            player.getInputs().addListener((ListChangeListener<InputItem>) c -> {
                if (c.getAddedSize() > 0){
                    List<? extends InputItem> items = c.getAddedSubList();
                    for (InputItem item : items) {
                        handleInput(item);
                    }
                }
            });
        }
    }

    public void handleInput(InputItem item){

    }

    private void initModel() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(user);
        game = new Game(players, user);
    }

    public void startGameLoop() {
        new Thread(() -> {
            while (game.getRunning()){
                if (!game.getPaused()){
                    game.update();
                }
            }
        }).start();
    }

    public GameScreen getView() {
        return view;
    }
}

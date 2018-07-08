package controller_layer;

import javafx.collections.ListChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
        System.out.println("init input handling");
        for (Player player : game.getPlayers()){
            player.getInputs().addListener((ListChangeListener<InputItem>) c -> {
                System.out.println("handling");
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
        System.out.println("handling input");
        if (item.getEvent().getEventType() == MouseEvent.MOUSE_PRESSED){
            user.updateRect(item.getEvent());
        } else if (item.getEvent().getEventType() == MouseEvent.MOUSE_RELEASED) {
            user.updateRect(item.getEvent());
        } else if (item.getEvent().getEventType() == MouseEvent.MOUSE_MOVED) {
            user.updateRect(item.getEvent());
        } else if (item.getEvent().getEventType() == KeyEvent.KEY_PRESSED) {

        }
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

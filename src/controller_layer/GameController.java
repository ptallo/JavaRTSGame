package controller_layer;

import javafx.collections.ListChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model_layer.Game;
import model_layer.InputItem;
import model_layer.Player;
import view_layer.GameScreen;

import java.util.*;

public class GameController {

    public static final long GAME_PERIOD = 1000 / 60;

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
        for (Player player : game.getPlayers()) {
            player.getInputs().addListener((ListChangeListener<InputItem>) c -> {
                c.next();
                if (c.getAddedSize() > 0) {
                    List<? extends InputItem> items = c.getAddedSubList();
                    for (InputItem item : items) {
                        handleInput(item);
                    }
                }
            });
        }
    }

    public void handleInput(InputItem item) {
        if (item.getEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
            user.updateRect((MouseEvent) item.getEvent());
        } else if (item.getEvent().getEventType() == MouseEvent.MOUSE_RELEASED) {
            user.updateRect((MouseEvent) item.getEvent());
        } else if (item.getEvent().getEventType() == MouseEvent.MOUSE_DRAGGED) {
            user.updateRect((MouseEvent) item.getEvent());
            user.updateTransformDirection((MouseEvent) item.getEvent(), view.getCanvas(), item.getSourceNode() == view.getCanvas());
        } else if (item.getEvent().getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (item.getEvent().getEventType() == MouseEvent.MOUSE_MOVED) {
            user.updateTransformDirection((MouseEvent) item.getEvent(), view.getCanvas(), item.getSourceNode() == view.getCanvas());
        }
    }

    private void initModel() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(user);
        game = new Game(players, user);
    }

    public void startGameLoop() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (game.getRunning() && !game.getPaused()) {
                    game.update();
                }
            }
        }, new Date(), 1000 / 60);
    }

    public GameScreen getView() {
        return view;
    }
}

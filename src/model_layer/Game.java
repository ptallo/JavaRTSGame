package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.physics.Rect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Serializable, GameObjectInterface {

    private Boolean running;
    private Boolean paused;

    private Player user;

    private ArrayList<Player> players;
    private HashMap<Player, ArrayList<GameObject>> playerToSelectedObjectMap = new HashMap<>();

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    public Game(ArrayList<Player> players, Player user) {
        this.players = players;
        players.forEach(player -> player.setGame(this));
        this.user = user;
        running = true;
        paused = false;

        gameObjects.add(new GameObject(20, 20, 50, 50));
        gameObjects.add(new GameObject(100, 20, 50, 50));
    }

    public void update(){
        for (GameObject object : gameObjects){
            object.update();
        }
    }

    public void draw(GraphicsContext gc) {
        for (GameObject object : gameObjects){
            object.draw(gc);
        }
        user.draw(gc);
    }

    public Boolean getPaused() {
        return paused;
    }

    public Boolean getRunning() {
        return running;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void selectUnits(Player player, Rect selectionRect) {
        ArrayList<GameObject> selectedObjects = new ArrayList<>();
        for (GameObject object : gameObjects){
            if (object.getPhysicsComponent().getRect().contains(selectionRect)){
                selectedObjects.add(object);
            }
        }
        System.out.println("This many units selected: " + selectedObjects.size());
        playerToSelectedObjectMap.put(player, selectedObjects);
    }
}

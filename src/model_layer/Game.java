package model_layer;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable, GameObjectInterface {

    private Boolean running;
    private Boolean paused;

    private Player user;

    private ArrayList<Player> players;

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    public Game(ArrayList<Player> players, Player user) {
        this.players = players;
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
}

package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.SelectionComponent;
import model_layer.components.SelectionSystem;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.PhysicsSystem;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Serializable {

    private Boolean running;
    private Boolean paused;

    private Player user;

    private ArrayList<Player> players;
    private HashMap<Player, ArrayList<GameObject>> playerToSelectedObjectMap = new HashMap<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private PhysicsSystem physicsSystem;
    private SelectionSystem selectionSystem;

    public Game(ArrayList<Player> players, Player user) {
        this.players = players;
        this.user = user;
        players.forEach(player -> player.setGame(this));
        running = true;
        paused = false;

        physicsSystem = new PhysicsSystem();
        selectionSystem = new SelectionSystem();

        gameObjects.add(new GameObject(20, 20));
        gameObjects.add(new GameObject(100, 100));
    }

    public void update(){
        for (GameObject object : gameObjects){
            ArrayList<GameObject> objects = new ArrayList<>(gameObjects);
            objects.remove(object);

            List<PhysicsComponent> physicsComponents = objects.stream().map(GameObject::getPhysicsComponent).collect(Collectors.toList());
            physicsSystem.update(object.getPhysicsComponent(), new ArrayList<>(physicsComponents));

            object.getSelectionComponent().setRect(object.getPhysicsComponent().getRectangle());
        }
    }

    public void draw(GraphicsContext gc) {
        for (GameObject object : gameObjects){
            physicsSystem.draw(gc, object.getPhysicsComponent());
            selectionSystem.draw(gc, object.getSelectionComponent());
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

    void selectUnits(Player player, Rectangle selectionRectangle) {
        ArrayList<GameObject> selectedObjects = new ArrayList<>();
        for (GameObject object : gameObjects){
            if (selectionSystem.checkSelected(selectionRectangle, object.getSelectionComponent())) {
                selectedObjects.add(object);
            }
        }
        System.out.println("This many units selected: " + selectedObjects.size());
        playerToSelectedObjectMap.put(player, selectedObjects);
    }

    void setObjectDestination(Player player, Point destination){
        System.out.println("setting destination");
        ArrayList<GameObject> objects = playerToSelectedObjectMap.get(player);
        if (objects != null && !objects.isEmpty()){
            objects.forEach(object -> object.getPhysicsComponent().setDestination(destination));
        }
    }
}

package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.ObjectInterface;
import model_layer.components.SelectionComponent;
import model_layer.components.SelectionSystem;
import model_layer.components.graphics.RenderSystem;
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
    private HashMap<Player, ArrayList<? extends ObjectInterface>> playerToSelectedObjectMap = new HashMap<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private PhysicsSystem physicsSystem;
    private SelectionSystem selectionSystem;
    private RenderSystem renderSystem;

    public Game(ArrayList<Player> players, Player user) {
        this.players = players;
        this.user = user;
        players.forEach(player -> player.setGame(this));
        running = true;
        paused = false;

        physicsSystem = new PhysicsSystem();
        selectionSystem = new SelectionSystem();
        renderSystem = new RenderSystem();

        gameObjects.add(new GameObject(20, 20, true));
        gameObjects.add(new GameObject(20, 100, true));
        gameObjects.add(new GameObject(100, 20, false));
        gameObjects.add(new GameObject(100, 100, false));
    }

    public void update(){
        for (GameObject object : gameObjects){
            ArrayList<GameObject> objects = new ArrayList<>(gameObjects);
            objects.remove(object);

            Boolean updated = physicsSystem.update(object.getPhysicsComponent(), objects);

            if (updated) {
                Rectangle newRect = object.getPhysicsComponent().getRectangle();
                object.getSelectionComponent().setRect(newRect);
                object.getRenderComponent().setDrawPoint(new Point(newRect.getX(), newRect.getY()));
                object.getRenderComponent().setCurrentAnimation("moving");
            } else {
                object.getRenderComponent().setCurrentAnimation("idle");
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (GameObject object : gameObjects){
            physicsSystem.draw(gc, object.getPhysicsComponent());
            selectionSystem.draw(gc, object.getSelectionComponent());
            renderSystem.draw(gc, object.getRenderComponent());
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
        ArrayList<? extends ObjectInterface> objects = playerToSelectedObjectMap.get(player);
        if (objects != null && !objects.isEmpty()){
            objects.forEach(object -> object.getPhysicsComponent().setDestination(destination));
        }
    }
}

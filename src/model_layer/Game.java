package model_layer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.components.SelectionSystem;
import model_layer.components.UnitCreationSystem;
import model_layer.components.graphics.RenderSystem;
import model_layer.components.physics.PhysicsSystem;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;
import model_layer.object_interface.map.Map;
import model_layer.object_interface.GameObject;
import model_layer.object_interface.ObjectInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Serializable {

    private Boolean running;
    private Boolean paused;

    private Player user;

    private ArrayList<Player> players;
    private HashMap<Player, ArrayList<ObjectInterface>> playerToSelectedObjectMap = new HashMap<>();
    private ArrayList<ObjectInterface> gameObjects = new ArrayList<>();

    private Map map;

    private PhysicsSystem physicsSystem;
    private SelectionSystem selectionSystem;
    private RenderSystem renderSystem;
    private UnitCreationSystem unitCreationSystem;

    public Game(ArrayList<Player> players, Player user) {
        this.players = players;
        this.user = user;
        players.forEach(player -> player.setGame(this));
        running = true;
        paused = false;

        physicsSystem = new PhysicsSystem();
        selectionSystem = new SelectionSystem();
        renderSystem = new RenderSystem();
        unitCreationSystem = new UnitCreationSystem();

        map = new Map();

        GameObject object = new GameObject(100, 100, true, 0);
        object.getUnitCreationComponent().addEntityToList(
                new GameObject(100, 100, true, 0.25),
                new Point(200, 100)
        );
        gameObjects.add(object);
        gameObjects.add(new GameObject(100, 200, true, 0.25));
    }

    public void update(){
        ArrayList<ObjectInterface> newObjects = new ArrayList<>();
        for (ObjectInterface object : gameObjects){
            // get list of gameobjects that doesn't have the current object
            ArrayList<ObjectInterface> objects = new ArrayList<>(gameObjects);
            objects.remove(object);

            if (object.getUnitCreationComponent() != null) {
                Rectangle rectangle = object.getPhysicsComponent().getRectangle();
                ObjectInterface newObject = unitCreationSystem.update(
                        object,
                        new Point(rectangle.getX(), rectangle.getY() + rectangle.getHeight())
                );
                if (newObject != null) {
                    newObjects.add(newObject);
                }
            }

            Rectangle previousRect = object.getPhysicsComponent().getRectangle();
            physicsSystem.update(object.getPhysicsComponent(), objects);
            Rectangle newRect = object.getPhysicsComponent().getRectangle();

            if (previousRect != newRect) { // object's position was updated
                object.getSelectionComponent().setRect(newRect);
                if (object.getRenderComponent() != null) {
                    object.getRenderComponent().setDrawPoint(new Point(newRect.getX(), newRect.getY()));
                    object.getRenderComponent().setCurrentAnimation("moving");
                }
            } else {
                if (object.getRenderComponent() != null) {
                    object.getRenderComponent().setCurrentAnimation("idle");
                }
            }
        }
        gameObjects.addAll(newObjects);
    }

    public void draw(GraphicsContext gc) {
        user.updateTransform(gc);

        map.draw();
        map.getMapTiles().forEach(mapTile -> renderSystem.draw(gc, mapTile.getRenderComponent()));
        map.getMapTiles().forEach(mapTile -> {
            Rectangle rectangle = mapTile.getRenderComponent().getDrawRect();
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        });

        ArrayList<ObjectInterface> drawObjects = new ArrayList<>(gameObjects);
        for (ObjectInterface object : drawObjects){
            physicsSystem.draw(gc, object.getPhysicsComponent());
            selectionSystem.draw(gc, object.getSelectionComponent());
            if (object.getRenderComponent() != null) {
                renderSystem.draw(gc, object.getRenderComponent());
            }
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
        ArrayList<ObjectInterface> selectedObjects = new ArrayList<>();
        for (ObjectInterface object : gameObjects){
            if (selectionSystem.checkSelected(selectionRectangle, object.getSelectionComponent())) {
                selectedObjects.add(object);
            }
        }
        playerToSelectedObjectMap.put(player, selectedObjects);
    }

    void setObjectDestination(Player player, Point destination){
        ArrayList<? extends ObjectInterface> objects = playerToSelectedObjectMap.get(player);
        if (objects != null && !objects.isEmpty()){
            objects.forEach(object -> {
                if (object.getPhysicsComponent() != null) {
                    object.getPhysicsComponent().addDestination(destination);
                }
            });
        }
    }
}

package model_layer.components;

import model_layer.components.physics.Point;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitCreationComponent {
    private ArrayList<ObjectInterface> entityCreationQueue = new ArrayList<>();
    private HashMap<ObjectInterface, Point> creationPoints = new HashMap<>(); //place object will be created relative to this object
    private long creationDuration;
    private long creationStarted;

    public UnitCreationComponent(long creationDuration) {
        this.creationDuration = creationDuration;
    }

    public long getCreationDuration() {
        return creationDuration;
    }

    public long getCreationStarted() {
        return creationStarted;
    }

    public void addEntityToList(ObjectInterface entity, Point point){
        entityCreationQueue.add(entity);
        creationPoints.put(entity, point);
        creationStarted = System.currentTimeMillis();
    }

    public ObjectInterface getEntityFromQueue(){
        if (entityCreationQueue.size() > 0){
            return entityCreationQueue.remove(0);
        }
        return null;
    }

    public HashMap<ObjectInterface, Point> getCreationPoints() {
        return creationPoints;
    }
}

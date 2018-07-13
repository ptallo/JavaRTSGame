package model_layer.components;

import model_layer.components.physics.Point;

import java.util.ArrayList;

public class UnitCreationComponent {
    private ArrayList<ObjectInterface> entityCreationQueue = new ArrayList<>();
    private Point creationDestination;
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

    public void addEntityToList(ObjectInterface entity){
        entityCreationQueue.add(entity);
        creationStarted = System.currentTimeMillis();
    }

    public ObjectInterface getObjectFromQueue(){
        return entityCreationQueue.remove(0);
    }

    public Point getCreationDestination() {
        return creationDestination;
    }

    public void setCreationDestination(Point creationDestination) {
        this.creationDestination = creationDestination;
    }
}

package model_layer;

import javafx.event.Event;
import javafx.scene.Node;

public class InputItem {

    private Event event;
    private Node sourceNode;
    private long timestamp;

    public InputItem(Event event, Node node) {
        sourceNode = node;
        timestamp = System.currentTimeMillis();
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Node getSourceNode() {
        return sourceNode;
    }
}

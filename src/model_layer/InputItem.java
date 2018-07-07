package model_layer;

import javafx.event.Event;

public class InputItem {

    private Event event;
    private long timestamp;

    public InputItem(Event event) {
        timestamp = System.currentTimeMillis();
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}

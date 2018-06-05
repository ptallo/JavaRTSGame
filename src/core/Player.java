package core;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    private String id;
    private Boolean ready;

    public Player() {
        id = UUID.randomUUID().toString();
        ready = false;
    }

    public String getId() {
        return id;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready){
        this.ready = ready;
    }
}

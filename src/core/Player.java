package core;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    private String id;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}

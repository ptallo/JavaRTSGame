package core;

import java.util.UUID;

public class Player {
    private String id;

    public Player() {
        id = UUID.randomUUID().toString();
    }
}

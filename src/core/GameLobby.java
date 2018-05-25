package core;

import java.util.ArrayList;
import java.util.UUID;

public class GameLobby {
    private String id;
    private ArrayList<Player> players = new ArrayList<>();
    private Player lobbyOwner;

    public GameLobby(Player owner) {
        id = UUID.randomUUID().toString();
        lobbyOwner = owner;
    }
}

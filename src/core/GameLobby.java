package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class GameLobby implements Serializable {
    private String id;
    private String lobbyName;
    private Player lobbyOwner;
    private ArrayList<Player> players = new ArrayList<>();

    public GameLobby(Player owner, String name) {
        id = UUID.randomUUID().toString();
        lobbyOwner = owner;
        lobbyName = name;
    }

    public void addPlayer(Player player){
        players.add(player);
    }
}

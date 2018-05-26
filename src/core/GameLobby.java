package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class GameLobby implements Serializable {
    private String id;
    private String lobbyName;
    private Player lobbyOwner;
    private int playerLimit;
    private ArrayList<Player> players = new ArrayList<>();

    public GameLobby(Player owner, String name, int playerLimit) {
        id = UUID.randomUUID().toString();
        lobbyOwner = owner;
        lobbyName = name;
        this.playerLimit = playerLimit;
    }

    public void addPlayer(Player player){
        if (players.size() < playerLimit) {
            players.add(player);
        } else {
            throw new ArrayIndexOutOfBoundsException("Player Limit Met!");
        }
    }

    public String getId() {
        return id;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}

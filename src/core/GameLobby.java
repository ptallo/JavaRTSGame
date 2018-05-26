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
        addPlayer(lobbyOwner);
        lobbyName = name;
        this.playerLimit = playerLimit;
    }

    public Boolean addPlayer(Player player){
        if (players.size() < playerLimit) {
            players.add(player);
            return true;
        } else {
            return false;
        }
    }

    public String getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}

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
    //TODO implement some way to associate a socket with a player so when a socket disconnects the player will leave the lobby

    public GameLobby(Player owner, String name, int playerLimit) {
        id = UUID.randomUUID().toString();
        lobbyOwner = owner;
        players.add(owner);
        lobbyName = name;
        this.playerLimit = playerLimit;
    }

    public Boolean addPlayer(Player player) {
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

    public Player getOwner(){
        return lobbyOwner;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public Boolean removePlayer(Player removePlayer) {
        Player playerToRemove = null;
        for (Player player : players){
            if (player.getId().equals(removePlayer.getId())) {
                playerToRemove = player;
            }
        }
        return players.remove(playerToRemove);
    }
}

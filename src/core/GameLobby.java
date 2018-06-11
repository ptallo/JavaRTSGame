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

    public GameLobby(GameLobby lobby){
        this.id = lobby.getId();
        this.lobbyName = lobby.getLobbyName();
        this.lobbyOwner = lobby.getOwner();
        this.playerLimit = lobby.getPlayerLimit();
        for (Player player : lobby.getPlayers()){
            Player temp = new Player(player);
            players.add(temp);
        }
        // TODO handler concurrent mod exception to players
    }

    public Boolean addPlayer(Player player) {
        if (players.size() < playerLimit) {
            players.add(player);
            return true;
        } else {
            return false;
        }
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

    public int getPlayerLimit() {
        return playerLimit;
    }
}

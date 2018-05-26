package networking.message;

import core.GameLobby;
import core.Player;

public class JoinLobbyMessage extends GameMessage<GameLobby> {

    private Player player;
    private Boolean joined = false;

    public JoinLobbyMessage(String message, GameLobby object, Player player) {
        super(message, object);
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public Boolean getJoined(){
        return joined;

    }
}

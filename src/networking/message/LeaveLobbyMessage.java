package networking.message;

import core.GameLobby;
import core.Player;

public class LeaveLobbyMessage extends GameMessage<GameLobby> {

    private Player player;

    public LeaveLobbyMessage(String message, GameLobby object, Player player) {
        super(message, object);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

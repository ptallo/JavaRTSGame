package networking.message;

import core.GameLobby;

public class LobbyMessage extends GameMessage<GameLobby> {
    public LobbyMessage(String message, GameLobby object) {
        super(message, object);
    }
}

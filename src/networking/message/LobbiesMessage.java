package networking.message;

import core.GameLobby;

import java.util.ArrayList;

public class LobbiesMessage extends GameMessage<ArrayList<GameLobby>> {

    public LobbiesMessage(String message, ArrayList<GameLobby> object) {
        super(message, object);
    }
}

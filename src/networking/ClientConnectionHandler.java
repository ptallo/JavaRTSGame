package networking;

import core.GameLobby;
import networking.messages.MessageType;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler extends ConnectionHandler {

    private final GameClient client;

    public ClientConnectionHandler(Socket socket, GameClient client) {
        super(socket);
        this.client = client;
    }

    @Override
    public void handleMessage(MessageType type) throws IOException, ClassNotFoundException {
        if (type == MessageType.GET_LOBBIES){
            client.getLobbyArrayList().clear();
            int numClients = ois.read();
            for (int i = 0; i < numClients; i++){
                client.getLobbyArrayList().add((GameLobby) ois.readObject());
            }
        }
    }
}

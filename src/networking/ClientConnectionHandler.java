package networking;

import core.GameLobby;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler extends ConnectionHandler {

    private final GameClient client;

    public ClientConnectionHandler(Socket socket, GameClient client) {
        super(socket);
        this.client = client;
    }

    @Override
    public void handleMessage(int messageType) throws IOException, ClassNotFoundException {
        System.out.println("handling message type: " + messageType);
        if (messageType == 1){
            client.getLobbyArrayList().clear();
            int numClients = ois.read();
            for (int i = 0; i < numClients; i++){
                client.getLobbyArrayList().add((GameLobby) ois.readObject());
            }
        }
    }
}

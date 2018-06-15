package networking;

import core.Game;
import core.GameLobby;
import gui.GameScreen;
import javafx.application.Platform;

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
        } else if (type == MessageType.START_GAME){
            int numObject = ois.read();
            Game game = (Game) ois.readObject();
            Platform.runLater(() -> {
                GameScreen screen = new GameScreen(GameClient.WIDTH, GameClient.HEIGHT, game);
                client.setScene(screen);
            });
        }
    }
}

package networking;

import core.Game;
import core.GameLobby;
import gui.GameScreen;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionHandler extends ConnectionHandler {

    private final GameClient client;
    private GameScreen gameScreen;

    public ClientConnectionHandler(Socket socket, GameClient client) {
        super(socket);
        this.client = client;
    }

    @Override
    public void handleMessage(MessageType type) throws IOException, ClassNotFoundException {
        if (type == MessageType.GET_LOBBIES){
            client.getLobbyArrayList().clear();
            int numClients = ois.read();
            ArrayList<GameLobby> lobbyArrayList = (ArrayList<GameLobby>) ois.readObject();
            client.getLobbyArrayList().addAll(lobbyArrayList);
        } else if (type == MessageType.START_GAME){
            int numObject = ois.read();
            Game game = (Game) ois.readObject();
            Platform.runLater(() -> {
                GameScreen screen = new GameScreen(game, client);
                this.gameScreen = screen;
                client.setScene(screen);
            });
        } else if (type == MessageType.SET_PLAYER_LOADED){
            int numObject = ois.read();
            Game game = (Game) ois.readObject();
            gameScreen.setGame(game);
            gameScreen.startGame();
        } else if (type == MessageType.SEND_GAME_TO_CLIENT){
            int numObject = ois.read();
            Game game = (Game) ois.readObject();
            gameScreen.setGame(game);
        }
    }
}

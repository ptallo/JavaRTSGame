package networking;

import core.GameLobby;
import core.Player;
import networking.messages.MessageType;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ServerConnectionHandler extends ConnectionHandler{

    public ServerConnectionHandler(Socket socket){
        super(socket);
    }

    @Override
    public void handleMessage(MessageType type) throws IOException, ClassNotFoundException {
        if (type == MessageType.GET_LOBBIES){
            sendGameLobbies();
        } else if (type == MessageType.CREATE_LOBBY){
            int numObjects = ois.read();
            GameLobby newLobby = (GameLobby) ois.readObject();
            GameServer.getLobbies().add(newLobby);

            sendGameLobbies();
        } else if (type == MessageType.ADD_PLAYER_TO_LOBBY) {
            int numObjects = ois.read();
            GameLobby newLobby = (GameLobby) ois.readObject();
            Player newPlayer = (Player) ois.readObject();

            for (GameLobby lobby : GameServer.getLobbies()) {
                if (lobby.getId().equals(newLobby.getId())) {
                    lobby.addPlayer(newPlayer);
                }
            }
        }
    }

    public void sendGameLobbies() throws IOException {
        List<Object> tempArray = new ArrayList<>();
        for (GameLobby lobby : GameServer.getLobbies()){
            GameLobby temp = new GameLobby(lobby);
            tempArray.add(temp);
        }
        sendListMessage(MessageType.GET_LOBBIES, tempArray);
    }
}

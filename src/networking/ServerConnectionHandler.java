package networking;

import core.GameLobby;
import core.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
        } else if (type == MessageType.SET_PLAYER_READY){
            int numObject = ois.read();
            GameLobby lobby = (GameLobby) ois.readObject();
            Player player = (Player) ois.readObject();

            for (GameLobby serverLobby : GameServer.getLobbies()){
                if (serverLobby.getId().equals(lobby.getId())){
                    for (Player lobbyPlayer : serverLobby.getPlayers()){
                        if (lobbyPlayer.getId().equals(player.getId())){
                            lobbyPlayer.setReady(player.getReady());
                        }
                    }
                }
            }
        } else if (type == MessageType.REMOVE_PLAYER_FROM_LOBBY){
            int numObject = ois.read();
            GameLobby lobby = (GameLobby) ois.readObject();
            Player player = (Player) ois.readObject();

            for (GameLobby serverLobby : GameServer.getLobbies()){
                if (serverLobby.getId().equals(lobby.getId())){
                    serverLobby.removePlayer(player);
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

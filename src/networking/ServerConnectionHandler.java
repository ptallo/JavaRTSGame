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
            List<Object> tempArray = new ArrayList<>();
            for (GameLobby lobby : GameServer.getLobbyIdToSocketMap().keySet()){
                GameLobby temp = new GameLobby(lobby);
                tempArray.add(temp);
            }
            sendListMessage(MessageType.GET_LOBBIES, tempArray);
        } else if (type == MessageType.CREATE_LOBBY){
            int numObjects = ois.read();
            GameLobby newLobby = (GameLobby) ois.readObject();

            ArrayList<ServerConnectionHandler> serverConnectionHandlers = new ArrayList<>();
            serverConnectionHandlers.add(this);
            GameServer.getLobbyIdToSocketMap().put(newLobby, serverConnectionHandlers);
        } else if (type == MessageType.ADD_PLAYER_TO_LOBBY) {
            int numObjects = ois.read();
            GameLobby newLobby = (GameLobby) ois.readObject();
            Player newPlayer = (Player) ois.readObject();

            for (GameLobby lobby : GameServer.getLobbyIdToSocketMap().keySet()) {
                if (lobby.getId().equals(newLobby.getId())) {
                    lobby.addPlayer(newPlayer);
                }
                ArrayList<ServerConnectionHandler> connectionHandlers = GameServer.getLobbyIdToSocketMap().get(lobby);
                connectionHandlers.add(this);
            }
        } else if (type == MessageType.SET_PLAYER_READY){
            int numObject = ois.read();
            GameLobby lobby = (GameLobby) ois.readObject();
            Player player = (Player) ois.readObject();

            for (GameLobby serverLobby : GameServer.getLobbyIdToSocketMap().keySet()){
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

            for (GameLobby serverLobby : GameServer.getLobbyIdToSocketMap().keySet()){
                if (serverLobby.getId().equals(lobby.getId())){
                    serverLobby.removePlayer(player);
                    if (serverLobby.getPlayers().size() == 0){
                        GameServer.getLobbyIdToSocketMap().remove(serverLobby);
                    }
                }
            }
        } else if (type == MessageType.START_GAME){
            int numObject = ois.read();
        }
    }
}

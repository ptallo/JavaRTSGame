package networking;

import core.Game;
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
            GameLobby lobby = (GameLobby) ois.readObject();

            ArrayList<ServerConnectionHandler> connectionHandlers = null;
            GameLobby gameLobby = null;
            for (GameLobby serverLobby : GameServer.getLobbyIdToSocketMap().keySet()){
                if (lobby.getId().equals(serverLobby.getId())){
                    connectionHandlers = GameServer.getLobbyIdToSocketMap().get(serverLobby);
                    gameLobby = serverLobby;
                }
            }

            if (connectionHandlers != null && gameLobby != null){
                Game game = new Game(gameLobby.getPlayers());
                GameServer.getGameToSocketMap().put(game, connectionHandlers);
                for (ServerConnectionHandler handler : connectionHandlers){
                    handler.sendMessage(MessageType.START_GAME, game);
                }
            }
        } else if (type == MessageType.SET_PLAYER_LOADED){
            int numObject = ois.read();
            Game game = (Game) ois.readObject();
            Player player = (Player) ois.readObject();

            for (Game serverGame : GameServer.getGameToSocketMap().keySet()){
                if (game.getId().equals(serverGame.getId())){
                    boolean playersLoaded = true;
                    for (Player gamePlayer : serverGame.getPlayers()){
                        if (gamePlayer.getId().equals(player.getId())){
                            gamePlayer.setLoaded(player.getLoaded());
                        }
                        if (!gamePlayer.getLoaded()){
                            playersLoaded = false;
                        }
                    }
                    if (playersLoaded){
                        for (ServerConnectionHandler handler : GameServer.getGameToSocketMap().get(serverGame)){
                            handler.sendMessage(MessageType.SET_PLAYER_LOADED, null);
                        }
                    }
                }
            }
        }
    }
}

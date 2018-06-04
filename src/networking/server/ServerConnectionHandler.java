package networking.server;


import core.GameLobby;
import core.Player;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream dout;
    private ObjectInputStream din;


    public ServerConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            dout = new ObjectOutputStream(socket.getOutputStream());
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int messageType = din.read();
                if (messageType == 1) {
                    // GetGameLobbies
                    ArrayList<GameLobby> lobbies = GameServer.getLobbies();
                    dout.writeInt(lobbies.size());
                    for (GameLobby lobby : lobbies) {
                        GameLobby clone = new GameLobby(lobby);
                        dout.writeObject(clone);
                    }
                    dout.flush();
                } else if (messageType == 2) {
                    // CreateGameLobby
                    GameLobby newLobby = (GameLobby) din.readObject();
                    GameServer.getLobbies().add(newLobby);
                } else if (messageType == 3) {
                    // JoinGameLobby
                    GameLobby newLobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    for (GameLobby lobby : GameServer.getLobbies()) {
                        if (lobby.getId().equals(newLobby.getId())) {
                            lobby.getPlayers().add(player);
                        }
                    }
                } else if (messageType == 4) {
                    // LeaveGameLobby
                    GameLobby newLobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    GameLobby lobbyToRemove = null;
                    for (GameLobby lobby : GameServer.getLobbies()) {
                        if (lobby.getId().equals(newLobby.getId())) {
                            Player playerToRemove = null;
                            for (Player serverPlayer : lobby.getPlayers()) {
                                if (serverPlayer.getId().equals(player.getId())) {
                                    playerToRemove = serverPlayer;
                                }
                            }
                            if (playerToRemove.getId().equals(lobby.getOwner().getId())) {
                                lobbyToRemove = lobby;
                            } else {
                                lobby.getPlayers().remove(playerToRemove);
                            }
                        }
                    }
                    if (lobbyToRemove != null) {
                        GameServer.getLobbies().remove(lobbyToRemove);
                    }
                } else if (messageType == -1) {
                    break;
                }
            }
            dout.close();
            din.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

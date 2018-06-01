package networking.server;


import core.GameLobby;
import core.Player;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                System.out.println("messageType " + messageType);
                if (messageType == 1) {
                    dout.reset();
                    dout.writeObject(GameServer.getLobbies());
                    dout.flush();
                } else if (messageType == 2) {
                    GameLobby newLobby = (GameLobby) din.readObject();
                    GameServer.getLobbies().add(newLobby);
                } else if (messageType == 3) {
                    GameLobby newLobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    for (GameLobby lobby : GameServer.getLobbies()) {
                        if (lobby.getId().equals(newLobby.getId())) {
                            lobby.getPlayers().add(player);
                        }
                    }
                } else if (messageType == 4) {
                    GameLobby newLobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    for (GameLobby lobby : GameServer.getLobbies()) {
                        if (lobby.getId().equals(newLobby.getId())) {
                            Player playerToRemove = null;
                            for (Player serverPlayer : lobby.getPlayers()){
                                if (serverPlayer.getId().equals(player.getId())) {
                                    playerToRemove = serverPlayer;
                                }
                            }
                            if (playerToRemove != null) {
                                lobby.getPlayers().remove(playerToRemove);
                            }
                        }
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

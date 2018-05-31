package networking.server;


import core.GameLobby;
import core.Player;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ServerConnectionHandler implements Runnable {

    private ObjectOutputStream dout;
    private ObjectInputStream din;

    public ServerConnectionHandler(Socket socket) {
        try {
            dout = new ObjectOutputStream(socket.getOutputStream());
            dout.flush();
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int messageType = din.readInt();
                if (messageType == 1) {
                    dout.reset();
                    dout.writeObject(GameServer.getLobbies());
                } else if (messageType == 2) {
                    GameLobby lobby = (GameLobby) din.readObject();
                    GameServer.getLobbies().add(lobby);
                } else if (messageType == 3) {
                    GameLobby lobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    Boolean added = false;
                    for (GameLobby serverLobby : GameServer.getLobbies()){
                        if (serverLobby.getId().equals(lobby.getId())) {
                            added = serverLobby.addPlayer(player);
                        }
                    }
                    dout.writeBoolean(added);
                    dout.flush();
                } else if (messageType == 4){
                    GameLobby lobby = (GameLobby) din.readObject();
                    Player player = (Player) din.readObject();
                    Boolean removed = false;
                    for (GameLobby serverLobby : GameServer.getLobbies()){
                        if (serverLobby.getId().equals(lobby.getId())) {
                            removed = serverLobby.removePlayer(player);
                        }
                    }
                    dout.writeBoolean(removed);
                    dout.flush();
                }
            } catch (EOFException e) {
                e.printStackTrace();
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

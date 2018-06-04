package networking.client;

import core.GameLobby;
import core.Player;
import networking.server.GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionHandler {

    private Socket socket;
    private ObjectOutputStream dout;
    private ObjectInputStream din;


    public ClientConnectionHandler() {
        try {
            socket = new Socket("localhost", GameServer.PORT);;
            dout = new ObjectOutputStream(socket.getOutputStream());
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameLobby> getGameLobbies() {
        ArrayList<GameLobby> lobbies = new ArrayList<>();
        try {
            dout.write(1);
            dout.flush();
            int numberLobbies = din.readInt();
            for (int i = 0; i < numberLobbies; i++) {
                GameLobby lobby = (GameLobby) din.readObject();
                lobbies.add(lobby);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lobbies;
    }

    public void createGameLobby(GameLobby lobby) {
        try {
            dout.write(2);
            dout.writeObject(lobby);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGameLobby(GameLobby lobby, Player player) {
        try {
            dout.write(3);
            dout.writeObject(lobby);
            dout.writeObject(player);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaveGameLobby(GameLobby lobby, Player player) {
        try {
            dout.write(4);
            dout.writeObject(lobby);
            dout.writeObject(player);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        din.close();
        dout.close();
        socket.close();
    }
}

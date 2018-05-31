package networking.client;

import core.GameLobby;
import core.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionHandler {

    private Socket socket;
    private ObjectOutputStream dout;
    private ObjectInputStream din;


    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            dout = new ObjectOutputStream(socket.getOutputStream());
            dout.flush();
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameLobby> getGameLobbies() {
        try {
            dout.reset();
            dout.writeInt(1);
            dout.flush();
            ArrayList<GameLobby> lobbies = (ArrayList<GameLobby>) din.readObject();
            return lobbies;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createGameLobby(GameLobby lobby) {
        try {
            dout.writeInt(2);
            dout.writeObject(lobby);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean joinGameLobby(GameLobby lobby, Player player) {
        try {
            dout.writeInt(3);
            dout.writeObject(lobby);
            dout.writeObject(player);
            dout.flush();
            return din.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean leaveGameLobby(GameLobby lobby, Player player) {
        try {
            dout.writeInt(4);
            dout.writeObject(lobby);
            dout.writeObject(player);
            dout.flush();
            return din.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException {
        socket.close();
        dout.close();
        din.close();
    }
}

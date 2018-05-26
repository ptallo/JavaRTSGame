package networking.client;

import core.GameLobby;
import core.Player;
import networking.message.JoinLobbyMessage;
import networking.message.LobbiesMessage;
import networking.message.LobbyMessage;

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
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameLobby> getGameLobbies(){
        try {
            LobbiesMessage message = new LobbiesMessage("GET", null);
            dout.reset();
            dout.writeObject(message);

            LobbiesMessage response = (LobbiesMessage) din.readObject();
            return response.getObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createGameLobby(GameLobby lobby){
        try {
            LobbyMessage message = new LobbyMessage("CREATE", lobby);
            dout.reset();
            dout.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameLobby joinGameLobby(GameLobby lobby, Player player){
        try {
            JoinLobbyMessage lobbyMessage = new JoinLobbyMessage("JOIN", lobby, player);
            dout.reset();
            dout.writeObject(lobbyMessage);

            JoinLobbyMessage returnMessage = (JoinLobbyMessage) din.readObject();
            if (returnMessage.getJoined()){
                return returnMessage.getObject();
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws IOException {
        socket.close();
    }
}

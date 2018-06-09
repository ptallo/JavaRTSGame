package networking;

import core.GameLobby;
import core.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    public static final int PORT = 8080;

    private static ArrayList<GameLobby> lobbies = new ArrayList<>();

    public static void main(String[] args) {
        lobbies.add(new GameLobby(new Player(), "test1" , 2));
        lobbies.add(new GameLobby(new Player(), "test2" , 2));
        try {
            ServerSocket server = new ServerSocket(PORT);
            while (true) {
                Socket socket = server.accept();
                ServerConnectionHandler handler = new ServerConnectionHandler(socket);
                Runnable runnable = handler::listenForMessages;
                new Thread(runnable).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<GameLobby> getLobbies() {
        return lobbies;
    }
}

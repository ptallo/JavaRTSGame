package networking;

import core.GameLobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameServer {

    public static final int PORT = 8080;

    private static HashMap<GameLobby, ArrayList<ServerConnectionHandler>> lobbyIdToSocketMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("listening for connectings...");
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

    public static HashMap<GameLobby, ArrayList<ServerConnectionHandler>> getLobbyIdToSocketMap() {
        return lobbyIdToSocketMap;
    }
}

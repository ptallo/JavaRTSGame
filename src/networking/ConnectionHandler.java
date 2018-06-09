package networking;

import core.GameLobby;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ConnectionHandler {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Boolean isConnected;

    public ConnectionHandler(Socket socket){
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessages(){
        while (isConnected) {
            try {
                int messageType = ois.read();
                if (messageType == 1) {
                    int size = GameServer.getLobbies().size();
                    for (GameLobby lobby : GameServer.getLobbies()) {
                        GameLobby temp = new GameLobby(lobby);
                        oos.writeObject(temp);
                    }
                }
            } catch (EOFException | SocketException e) {
                isConnected = false;
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(){
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

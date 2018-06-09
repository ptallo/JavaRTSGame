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

public class ServerConnectionHandler extends ConnectionHandler{

    public ServerConnectionHandler(Socket socket){
        super(socket);
    }

    @Override
    public void handleMessage(int messageType) throws IOException {
        System.out.println("handling message type: " + messageType);
        if (messageType == 1){
            int size = GameServer.getLobbies().size();
            oos.write(1);
            oos.write(size);
            for (GameLobby lobby : GameServer.getLobbies()){
                GameLobby temp = new GameLobby(lobby);
                oos.writeObject(temp);
            }
            oos.flush();
        }
    }
}

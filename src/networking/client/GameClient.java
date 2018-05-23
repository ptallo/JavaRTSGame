package networking.client;

import networking.server.GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", GameServer.PORT);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

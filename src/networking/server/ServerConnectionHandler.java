package networking.server;

import networking.message.GameMessage;
import networking.message.LobbiesMessage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler implements Runnable {

    private ObjectOutputStream dout;
    private ObjectInputStream din;
    private ArrayList<GameMessage> messageQueue = new ArrayList<>();
    private boolean isConnected;

    public ServerConnectionHandler(Socket socket) {
        try {
            dout = new ObjectOutputStream(socket.getOutputStream());
            din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        isConnected = true;
    }

    @Override
    public void run() {
        try {
            while (isConnected) {
                if (!messageQueue.isEmpty()) {
                    GameMessage message = messageQueue.get(0);
                    messageQueue.remove(0);
                    dout.writeObject(message);
                }

                GameMessage message = (GameMessage) din.readObject();
                if (message != null) {
                    if (message instanceof LobbiesMessage) {
                        LobbiesMessage sendMessage = new LobbiesMessage("Sending Lobbies", GameServer.getLobbies());
                        messageQueue.add(sendMessage);
                    }
                }
            }
        } catch (EOFException e) {
            isConnected = false;
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

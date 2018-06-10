package networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public abstract class ConnectionHandler {

    protected Socket socket;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;

    protected Boolean isConnected;

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

    public abstract void handleMessage(int messageType) throws IOException, ClassNotFoundException;

    public void listenForMessages(){
        while (isConnected) {
            try {
                int messageType = ois.read();
                if (messageType == -1) {
                    System.out.println("client disconnected...");
                    throw new EOFException("-1 message received");
                }
                handleMessage(messageType);
            } catch (EOFException | SocketException e) {
                isConnected = false;
                close();
            } catch (IOException | ClassNotFoundException e) {
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

    public void sendMessage(int messageType, Object... objects) throws IOException {
        System.out.println("sending message type: " + messageType);
        oos.write(messageType);
        if (objects != null){
            for (Object object : objects){
                oos.writeObject(object);
            }
        }
        oos.flush();
    }
}

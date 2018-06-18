package networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

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

    public abstract void handleMessage(MessageType type) throws IOException, ClassNotFoundException;

    public void listenForMessages(){
        while (isConnected) {
            try {
                int value = ois.read();
                if (value == -1) {
                    throw new EOFException("-1 message received, client disconnected");
                }
                MessageType type = MessageType.getMessageType(value);
                handleMessage(type);
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

    public void sendMessage(MessageType messageType, Object... objects) throws IOException {
        oos.write(messageType.getValue());
        if (objects != null){
            oos.write(objects.length);
            for (Object object : objects){
                oos.writeObject(object);
            }
        }
        oos.flush();
    }
}

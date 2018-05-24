package networking.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientConnectionHandler {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;

    public ClientConnectionHandler(Socket socket, DataInputStream din, DataOutputStream dout) {
        this.socket = socket;
        this.din = din;
        this.dout = dout;
    }
}

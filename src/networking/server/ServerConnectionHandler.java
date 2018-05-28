package networking.server;

import core.GameLobby;
import core.Player;
import networking.message.*;

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
                    dout.reset();
                    dout.writeObject(message);
                }

                GameMessage message = (GameMessage) din.readObject();
                if (message != null) {
                    if (message instanceof LobbiesMessage) {
                        LobbiesMessage sendMessage = new LobbiesMessage("Sending Lobbies", GameServer.getLobbies());
                        messageQueue.add(sendMessage);
                    } else if (message instanceof LobbyMessage) {
                        GameServer.getLobbies().add(((LobbyMessage) message).getObject());
                    } else if (message instanceof JoinLobbyMessage) {
                        JoinLobbyMessage joinLobbyMessage = (JoinLobbyMessage) message;
                        for (GameLobby lobby : GameServer.getLobbies()) {
                            if (lobby.getId().equals(joinLobbyMessage.getObject().getId())) {
                                Boolean added = lobby.addPlayer(joinLobbyMessage.getPlayer());
                                JoinLobbyMessage returnMessage = new JoinLobbyMessage("JOIN RETURN", lobby, joinLobbyMessage.getPlayer());
                                returnMessage.setJoined(added);
                                dout.writeObject(returnMessage);
                            }
                        }
                    } else if (message instanceof LeaveLobbyMessage) {
                        LeaveLobbyMessage leaveLobbyMessage = (LeaveLobbyMessage) message;
                        GameLobby lobby = leaveLobbyMessage.getObject();
                        Player player = leaveLobbyMessage.getPlayer();
                        GameLobby lobbyToRemove = null;
                        for (GameLobby serverLobby : GameServer.getLobbies()) {
                            if (serverLobby.getId().equals(lobby.getId())) {
                                serverLobby.getPlayers().remove(player);
                                if (serverLobby.getOwner().getId().equals(player.getId())) {
                                    lobbyToRemove = serverLobby;
                                }
                            }
                        }
                        GameServer.getLobbies().remove(lobbyToRemove);
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("CLIENT DISONNECTED!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

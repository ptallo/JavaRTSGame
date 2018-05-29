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

                        GameLobby serverLobby = null;
                        Player playerToRemove = null;
                        for (GameLobby lobby1 : GameServer.getLobbies()) {
                            if (lobby1.getId().equals(lobby.getId())) {
                                for (Player player1 : lobby1.getPlayers()) {
                                    if (player1.getId().equals(player.getId())) {
                                        serverLobby = lobby1;
                                        playerToRemove = player1;
                                    }
                                }
                            }
                        }
                        serverLobby.getPlayers().remove(playerToRemove);
                        if (serverLobby.getOwner().getId().equals(playerToRemove.getId())) {
                            GameServer.getLobbies().remove(serverLobby);
                        }
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

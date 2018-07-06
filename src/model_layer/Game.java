package model_layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Game implements Serializable {

    private ArrayList<Player> players;
    private String id;
    private boolean running;
    private int count;

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.id = UUID.randomUUID().toString();
        running = true;
    }

    public void update(){
        count += 1;
    }

    public void draw(){

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public boolean isRunning() {
        return running;
    }

    public int getCount() {
        return count;
    }
}

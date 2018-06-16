package core;

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

    public Game(Game game){
        players = new ArrayList<>();
        for (Player player : game.getPlayers()){
            Player temp = new Player(player);
            players.add(temp);
        }
        this.id = game.id;
        this.running = game.running;
        this.count = game.count;
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

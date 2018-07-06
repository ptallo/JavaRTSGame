package model_layer;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

    private ArrayList<Player> players;
    private Boolean running;
    private Boolean paused;
    private Integer count;

    public Game(ArrayList<Player> players) {
        this.players = players;
        running = true;
        paused = false;
    }

    public void update(){
        System.out.println("updating" + count);
        count += 1;
    }

    public Boolean getPaused() {
        return paused;
    }

    public Boolean getRunning() {
        return running;
    }

    public int getCount() {
        return count;
    }
}

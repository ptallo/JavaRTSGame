package model_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {
    private String id;
    private Boolean ready;
    private Boolean loaded;

    private ObservableList<InputItem> inputs;

    public Player() {
        id = UUID.randomUUID().toString();
        ready = false;
        loaded = false;
    }

    public Player(Player player) {
        this.id = player.getId();
        this.ready = player.getReady();
        this.loaded = player.getLoaded();
    }

    public String getId() {
        return id;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Boolean getLoaded() {
        return loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public void addInput(InputItem item) {
        ArrayList<InputItem> items = new ArrayList<>();
        if (this.inputs != null) {
            items.addAll(inputs);
        }
        items.add(item);
        this.inputs = FXCollections.observableList(items);
    }
}

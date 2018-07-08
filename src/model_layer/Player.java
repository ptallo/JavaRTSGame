package model_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {

    private String id;
    private ObservableList<InputItem> inputs = FXCollections.observableList(new ArrayList<>());

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void addInput(InputItem item) {
        ArrayList<InputItem> items = new ArrayList<>();
        items.addAll(inputs);
        items.add(item);
        this.inputs = FXCollections.observableList(items);
    }

    public ObservableList<InputItem> getInputs() {
        return inputs;
    }
}

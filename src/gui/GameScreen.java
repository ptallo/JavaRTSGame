package gui;

import javafx.scene.layout.GridPane;
import networking.client.GameClient;

public class GameScreen extends GridPane {

    private double width;
    private double height;
    private GameClient client;


    public GameScreen(double width, double height, GameClient client) {
        this.width = width;
        this.height = height;
        this.client = client;
    }

}

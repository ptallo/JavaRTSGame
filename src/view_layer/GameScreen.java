package view_layer;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model_layer.Game;

public class GameScreen extends GridPane {

    private Game game;
    private Text text;

    private GraphicsContext gc;
    private Canvas canvas;

    public GameScreen(int width, int height, Game game) {
        this.game = game;

        setPrefSize(width, height);
        setPadding(new Insets(20));
        setHgap(10);
        setVgap(10);

        canvas = new Canvas(width, height * 0.8);
        add(canvas, 0, 0);

        gc = canvas.getGraphicsContext2D();
        gc.setFill( Color.RED );
        gc.setLineWidth(2);
        Font font = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( font );
    }

    public void drawGame() {
        new Thread(() -> {
            while (game.getRunning() && !game.getPaused()){
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
            }
        }).start();
    }
}

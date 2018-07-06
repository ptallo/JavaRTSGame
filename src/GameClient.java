import core.Player;
import gui.GameScreen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameClient extends Application {

    public static int HEIGHT = 512;
    public static int WIDTH = 512;

    private Stage primaryStage;
    private Player player = new Player();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        GameScreen screen = new GameScreen(WIDTH, HEIGHT);
        root.getChildren().add(screen);

        primaryStage.show();
    }
}

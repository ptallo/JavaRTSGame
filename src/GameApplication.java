import controller_layer.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model_layer.Player;

public class GameApplication extends Application {

    private Stage primaryStage;
    private Player user = new Player();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("RTS Game Client");

        Group root = new Group();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);

        GameController controller = new GameController(user);

        root.getChildren().add(controller.getView());
        primaryStage.show();

        controller.startGameLoop();
        controller.getView().drawGame(primaryStage.getWidth(), primaryStage.getHeight());
    }
}

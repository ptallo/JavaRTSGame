package view_layer;

import javafx.scene.paint.Color;
import model_layer.Game;
import model_layer.Player;
import model_layer.components.Rectangle;
import model_layer.object_interface.ObjectInterface;
import model_layer.object_interface.map.MapTile;

import java.util.ArrayList;

public class MinimapPane extends GuiPane {

    public MinimapPane(Double width, Double height) {
        super(width, height);
    }


    @Override
    protected void initEventHandlers() {

    }

    @Override
    protected void populateUI(Game game, Player user) {
        Rectangle mapRectangle = game.getMap().getMapRectangle();
        Double xScale = canvas.getWidth() / mapRectangle.getWidth();
        Double yScale = canvas.getHeight() / mapRectangle.getHeight();

        drawMapTiles(game, xScale, yScale);

        drawGameObjects(game, xScale, yScale);

        drawUserScreen(user, xScale, yScale);
    }

    private void drawMapTiles(Game game, Double xScale, Double yScale) {
        for (MapTile tile : game.getMap().getMapTiles()) {
            Color tileColor = tile.getRenderComponent().getImage().getPixelReader().getColor(0, 0);
            gc.setFill(tileColor);
            Rectangle drawRect = tile.getRenderComponent().getDrawRect();
            gc.fillRect(
                    drawRect.getX() * xScale,
                    drawRect.getY() * yScale,
                    drawRect.getWidth() * xScale,
                    drawRect.getHeight() * yScale
            );
        }
    }

    private void drawGameObjects(Game game, Double xScale, Double yScale) {
        ArrayList<ObjectInterface> objects = new ArrayList<>(game.getGameObjects());
        for (ObjectInterface objectInterface : objects) {
            Color tileColor = objectInterface.getOwner().getPlayerColor();
            gc.setFill(tileColor);
            Rectangle drawRect = objectInterface.getRenderComponent().getDrawRect();
            gc.fillRect(
                    drawRect.getX() * xScale,
                    drawRect.getY() * yScale,
                    drawRect.getWidth() * xScale,
                    drawRect.getHeight() * yScale
            );
        }
    }

    private void drawUserScreen(Player user, Double xScale, Double yScale) {
        Rectangle rectangle = user.getScreenRectangle();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(
                rectangle.getX() * xScale,
                rectangle.getY() * yScale,
                rectangle.getWidth() * xScale,
                rectangle.getHeight() * yScale
        );
    }
}

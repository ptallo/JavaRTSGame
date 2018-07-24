package model_layer.object_interface.map;

import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Map {

    private ArrayList<String> mapDef = new ArrayList<>();
    private ArrayList<ArrayList<MapTile>> mapTiles;

    public Map(){
        mapDef.add("1111111111111112222");
        mapDef.add("1111111112221111111");
        mapDef.add("1111111112221111111");
        mapDef.add("1111111112221111111");
        mapDef.add("1111111111111111111");
        mapDef.add("1111111111111111111");
        mapDef.add("1111111111111111111");
        mapDef.add("1111111111111111111");
        initMapTiles();
    }

    public void initMapTiles(){
        mapTiles = new ArrayList<>();
        for (String rowDef : mapDef){
            ArrayList<MapTile> row = new ArrayList<>();
            for (char c : rowDef.toCharArray()){
                MapTile tile = MapDefEnum.getInstanceForId(c);
                if (tile != null) {
                    row.add(tile);
                }
            }
            mapTiles.add(row);
        }
    }

    public void draw(){
        int rowIndex = 0;
        for (ArrayList<MapTile> row : mapTiles){
            int tileIndex = 0;
            for (MapTile tile : row){
                Point tilePoint = new Point(tileIndex * MapTile.WIDTH, rowIndex * MapTile.HEIGHT);
                tile.getPhysicsComponent().setRectangleOrigin(tilePoint);
                tile.getRenderComponent().setDrawPoint(tilePoint);
                tileIndex++;
            }
            rowIndex++;
        }
    }

    public ArrayList<MapTile> getMapTiles(){
        ArrayList<MapTile> allMapTiles = new ArrayList<>();
        for (ArrayList<MapTile> row : mapTiles){
            allMapTiles.addAll(row);
        }
        return allMapTiles;
    }

    public Rectangle getMapRectangle(){
        List<Rectangle> tileRects = getMapTiles().stream()
                .map(MapTile::getPhysicsComponent)
                .map(PhysicsComponent::getRectangle)
                .collect(Collectors.toList());

        Double x = Collections.min(tileRects.stream().map(Rectangle::getX).collect(Collectors.toList()));
        Double y = Collections.min(tileRects.stream().map(Rectangle::getY).collect(Collectors.toList()));
        Double maxX = Collections.max(tileRects.stream().map(rectangle -> rectangle.getX() + rectangle.getWidth()).collect(Collectors.toList()));
        Double maxY = Collections.max(tileRects.stream().map(rectangle -> rectangle.getY() + rectangle.getHeight()).collect(Collectors.toList()));

        return new Rectangle(x, y, maxX - x, maxY - y);
    }
}

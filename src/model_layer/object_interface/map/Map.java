package model_layer.object_interface.map;

import model_layer.components.physics.Point;

import java.util.ArrayList;

public class Map {

    private ArrayList<String> mapDef = new ArrayList<>();
    private ArrayList<ArrayList<MapTile>> mapTiles;

    public Map(){
        mapDef.add("1112211");
        mapDef.add("1112211");
        mapDef.add("1111111");
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
                tile.getRenderComponent().setDrawPoint(new Point(tileIndex * MapTile.WIDTH, rowIndex * MapTile.HEIGHT));
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
}

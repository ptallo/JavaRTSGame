package model_layer.object_interface.map;

public enum MapDefEnum {
    GRASSTILE('1', "grasstile.png", false),
    SANDTILE('2', "sandtile.png", true);

    private char id;
    private String tilePath;
    private Boolean isCollidable;

    MapDefEnum(char id, String tilePath, Boolean isCollidable) {
        this.id = id;
        this.tilePath = tilePath;
        this.isCollidable = isCollidable;
    }

    public static MapTile getInstanceForId(char c){
        for (MapDefEnum value : MapDefEnum.values()){
            if (value.getId() == c){
                return new MapTile(0, 0, value.getTilePath(), value.getCollidable());
            }
        }
        return null;
    }

    public char getId() {
        return id;
    }

    public String getTilePath() {
        return tilePath;
    }

    public Boolean getCollidable() {
        return isCollidable;
    }
}

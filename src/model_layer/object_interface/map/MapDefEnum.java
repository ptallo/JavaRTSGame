package model_layer.object_interface.map;

public enum MapDefEnum {
    GRASSTILE('1', "grasstile.png", 1),
    SANDTILE('2', "sandtile.png", 0.25);

    private char id;
    private String tilePath;
    private Double speedMultiplier;

    MapDefEnum(char id, String tilePath, double speedMultiplier) {
        this.id = id;
        this.tilePath = tilePath;
        this.speedMultiplier = speedMultiplier;
    }

    public static MapTile getInstanceForId(char c){
        for (MapDefEnum value : MapDefEnum.values()){
            if (value.getId() == c){
                return new MapTile(0, 0, value.getTilePath(), value.getSpeedMultiplier());
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

    public Double getSpeedMultiplier() {
        return speedMultiplier;
    }
}

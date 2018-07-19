package model_layer.object_interface.map;

public enum MapDefEnum {
    GRASSTILE("1", "grasstile.png"),
    SANDTILE("2", "sandtile.png");

    private String id;
    private String tilePath;

    MapDefEnum(String identifier, String path) {
        id = identifier;
        tilePath = path;
    }

    public static String getPathForID(char c){
        for (MapDefEnum value : MapDefEnum.values()){
            if (value.getId().equalsIgnoreCase(String.valueOf(c))){
                return value.getTilePath();
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public String getTilePath() {
        return tilePath;
    }
}

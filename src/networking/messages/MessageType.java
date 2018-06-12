package networking.messages;

public enum MessageType {
    GET_LOBBIES, CREATE_LOBBY, ADD_PLAYER_TO_LOBBY, REMOVE_PLAYER_FROM_LOBBY;

    private int value;

    static {
        GET_LOBBIES.value = 1;
        CREATE_LOBBY.value = 2;
        ADD_PLAYER_TO_LOBBY.value = 3;
        REMOVE_PLAYER_FROM_LOBBY.value = 4;
    }

    public int getValue(){
        return value;
    }

    public static MessageType getMessageType(int value){
        for (MessageType type : MessageType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }
}

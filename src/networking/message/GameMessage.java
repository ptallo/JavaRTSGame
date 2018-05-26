package networking.message;

import java.io.Serializable;

public abstract class GameMessage<T> implements Serializable {

    private String message;
    private T object;

    public GameMessage(String message, T object) {
        this.message = message;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }
}

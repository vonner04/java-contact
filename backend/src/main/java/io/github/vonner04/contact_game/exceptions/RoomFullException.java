package io.github.vonner04.contact_game.exceptions;

public class RoomFullException extends RuntimeException {
    public RoomFullException() {
        super();
    }

    public RoomFullException(String message) {
        super(message);
    }

    public RoomFullException(String message, Throwable cause) {
        super(message, cause);
    }
}

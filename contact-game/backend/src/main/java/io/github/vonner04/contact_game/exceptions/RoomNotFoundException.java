package io.github.vonner04.contact_game.exceptions;

public class RoomNotFoundException extends IllegalArgumentException {

    public RoomNotFoundException() {
        super();
    }

    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
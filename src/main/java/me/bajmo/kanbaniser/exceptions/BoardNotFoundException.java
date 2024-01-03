package me.bajmo.kanbaniser.exceptions;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException(String message) {
        super(message);
    }

}
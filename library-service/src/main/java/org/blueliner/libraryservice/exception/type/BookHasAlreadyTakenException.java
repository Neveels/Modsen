package org.blueliner.libraryservice.exception.type;

public class BookHasAlreadyTakenException extends RuntimeException {
    public BookHasAlreadyTakenException(String message) {
        super(message);
    }
}

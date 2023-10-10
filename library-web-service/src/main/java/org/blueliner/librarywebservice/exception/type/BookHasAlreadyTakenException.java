package org.blueliner.librarywebservice.exception.type;

public class BookHasAlreadyTakenException extends RuntimeException {
    public BookHasAlreadyTakenException(String message) {
        super(message);
    }
}

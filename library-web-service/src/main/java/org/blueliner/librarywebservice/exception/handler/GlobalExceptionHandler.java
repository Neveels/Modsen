package org.blueliner.librarywebservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.blueliner.librarywebservice.exception.ErrorResponse;
import org.blueliner.librarywebservice.exception.type.BookHasAlreadyTakenException;
import org.blueliner.librarywebservice.exception.type.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BookNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BookNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(value = BookHasAlreadyTakenException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BookHasAlreadyTakenException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.ok(errorResponse);
    }

}

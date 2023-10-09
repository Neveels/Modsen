package org.blueliner.libraryservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.blueliner.libraryservice.exception.ErrorResponse;
import org.blueliner.libraryservice.exception.type.BookLogNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BookLogNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BookLogNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(errorResponse);
    }

}

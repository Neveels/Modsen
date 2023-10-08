package org.blueliner.authenticationservice.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.blueliner.authenticationservice.exception.ErrorResponse;
import org.blueliner.authenticationservice.exception.type.UserAlreadyExist;
import org.blueliner.authenticationservice.exception.type.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(UserAlreadyExist e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.ok(errorResponse);
    }

}

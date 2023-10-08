package org.blueliner.librarywebservice.exception;

import lombok.SneakyThrows;
import org.blueliner.librarywebservice.exception.type.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private static final String BOOK_NOT_FOUND_EXCEPTION = "Book not found";

    @Override
    @SneakyThrows
    public boolean hasError(ClientHttpResponse response) {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    @SneakyThrows
    public void handleError(ClientHttpResponse response) {
        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new BookNotFoundException(BOOK_NOT_FOUND_EXCEPTION);
        }
    }

}
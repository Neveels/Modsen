package org.blueliner.librarywebservice.service;


import org.blueliner.librarywebservice.dto.request.BookRequest;
import org.blueliner.librarywebservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.librarywebservice.dto.response.BookLogResponse;
import org.blueliner.librarywebservice.dto.response.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibraryService {
    BookResponse createBook(BookRequest body);

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    BookResponse updateBook(Long id, BookRequest body);

    void deleteBookById(Long id);

    List<BookResponse> getAllBooksByISBN(String isbn);

    List<BookResponse> getFreeBooks();

    ResponseEntity<BookLogResponse> changeBookStatus(Long id, PutBookInTheStorageDto putBookInTheStorageDto);

}

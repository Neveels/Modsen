package org.blueliner.librarywebservice.service;


import org.blueliner.librarywebservice.dto.request.BookRequest;
import org.blueliner.librarywebservice.dto.response.BookResponse;

import java.util.List;

public interface LibraryService {
    BookResponse createBook(BookRequest body);

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    BookResponse updateBook(Long id, BookRequest body);

    void deleteBookById(Long id);

    BookResponse getBookByISBN(String isbn);

    List<BookResponse> getFreeBooks();
}

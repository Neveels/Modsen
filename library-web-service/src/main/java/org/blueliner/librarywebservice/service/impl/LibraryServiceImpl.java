package org.blueliner.librarywebservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blueliner.librarywebservice.dto.GetFreeBookResponse;
import org.blueliner.librarywebservice.dto.request.BookRequest;
import org.blueliner.librarywebservice.dto.response.BookResponse;
import org.blueliner.librarywebservice.endpoint.LocalLibraryServiceEndpoint;
import org.blueliner.librarywebservice.exception.type.BookNotFoundException;
import org.blueliner.librarywebservice.kafka.Producer;
import org.blueliner.librarywebservice.model.Book;
import org.blueliner.librarywebservice.repository.BookRepository;
import org.blueliner.librarywebservice.service.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final String BOOK_WITH_ID_NOT_FOUND_EXCEPTION = "Book with id %s not found!";
    private final String BOOK_WITH_ISBN_NOT_FOUND_EXCEPTION = "Book with isbn %s not found!";
    private final BookRepository bookRepository;
    private final Producer producer;
    private final ModelMapper modelMapper;
    private final LocalLibraryServiceEndpoint localLibraryServiceEndpoint;

    @Override
    public BookResponse createBook(BookRequest body) {
        Book book = modelMapper.map(body, Book.class);
        producer.sendKafkaBookIdAdditionDtoToConsumer(book.getId());
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(
                        String.format(BOOK_WITH_ID_NOT_FOUND_EXCEPTION, id)
                )
        );
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest body) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(
                        String.format(BOOK_WITH_ID_NOT_FOUND_EXCEPTION, id)
                )
        );
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponse getBookByISBN(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() ->
                new BookNotFoundException(
                        String.format(BOOK_WITH_ISBN_NOT_FOUND_EXCEPTION, isbn)
                )
        );
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> getFreeBooks() {
        List<Long> longIdsList = Objects.requireNonNull(localLibraryServiceEndpoint.getIdsOfBusyBooks().getBody())
                .stream()
                .map(GetFreeBookResponse::bookId)
                .toList();
        return bookRepository.findAll()
                .stream()
                .filter(book -> !longIdsList.contains(book.getId()))
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }
}

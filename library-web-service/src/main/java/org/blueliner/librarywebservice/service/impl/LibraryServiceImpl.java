package org.blueliner.librarywebservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blueliner.librarywebservice.dto.request.BookRequest;
import org.blueliner.librarywebservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.librarywebservice.dto.response.BookLogResponse;
import org.blueliner.librarywebservice.dto.response.BookResponse;
import org.blueliner.librarywebservice.dto.response.GetFreeBookResponse;
import org.blueliner.librarywebservice.endpoint.LocalLibraryServiceEndpoint;
import org.blueliner.librarywebservice.exception.type.BookNotFoundException;
import org.blueliner.librarywebservice.kafka.Producer;
import org.blueliner.librarywebservice.model.Book;
import org.blueliner.librarywebservice.repository.BookRepository;
import org.blueliner.librarywebservice.service.LibraryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final String BOOK_WITH_ID_NOT_FOUND_EXCEPTION = "Book with id %s not found!";
    private final BookRepository bookRepository;
    private final Producer producer;
    private final ModelMapper modelMapper;
    private final LocalLibraryServiceEndpoint localLibraryServiceEndpoint;

    @Override
    public BookResponse createBook(BookRequest body) {
        Book book = modelMapper.map(body, Book.class);
        Book saveBook = bookRepository.save(book);
        producer.sendKafkaBookIdAdditionDtoToConsumer(saveBook.getId());
        return modelMapper.map(saveBook, BookResponse.class);
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
        Book book = getBookByIdFromDb(id);
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest body) {
        return bookRepository.findById(id)
                .map(book -> {
                    Book mapBook = modelMapper.map(body, Book.class);
                    mapBook.setId(book.getId());
                    bookRepository.save(mapBook);
                    return modelMapper.map(bookRepository.save(mapBook), BookResponse.class);
                })
                .orElseThrow(() ->
                        new BookNotFoundException(String.format(BOOK_WITH_ID_NOT_FOUND_EXCEPTION, id))
                );
    }

    @Override
    public void deleteBookById(Long id) {
        Book book = getBookByIdFromDb(id);
        bookRepository.delete(book);
    }

    @Override
    public List<BookResponse> getAllBooksByISBN(String isbn) {
        return bookRepository.findAllByIsbn(isbn)
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
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

    @Override
    public ResponseEntity<BookLogResponse> changeBookStatus(Long id, PutBookInTheStorageDto putBookInTheStorageDto) {
        return localLibraryServiceEndpoint.changeBookStatus(id, putBookInTheStorageDto);
    }


    private Book getBookByIdFromDb(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(
                        String.format(BOOK_WITH_ID_NOT_FOUND_EXCEPTION, id)
                )
        );
    }
    
}

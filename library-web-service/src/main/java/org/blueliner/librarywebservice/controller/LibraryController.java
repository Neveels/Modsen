package org.blueliner.librarywebservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blueliner.librarywebservice.dto.request.BookRequest;
import org.blueliner.librarywebservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.librarywebservice.dto.response.BookLogResponse;
import org.blueliner.librarywebservice.dto.response.BookResponse;
import org.blueliner.librarywebservice.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping()
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest body) {
        return ResponseEntity
                .ok()
                .body(libraryService.createBook(body));
    }

    @GetMapping()
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity
                .ok()
                .body(libraryService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(libraryService.getBookById(id));
    }

    @GetMapping("/isnb/{isbn}")
    public ResponseEntity<List<BookResponse>> getAllBooksByISBN(@PathVariable String isbn) {
        return ResponseEntity
                .ok()
                .body(libraryService.getAllBooksByISBN(isbn));
    }

    @GetMapping("/free")
    public ResponseEntity<List<BookResponse>> getFreeBooks() {
        return ResponseEntity
                .ok()
                .body(libraryService.getFreeBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @RequestBody BookRequest body) {
        return ResponseEntity
                .ok()
                .body(libraryService.updateBook(id, body));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BookLogResponse> updateBookStatus(@PathVariable Long id,
                                                            @RequestBody PutBookInTheStorageDto putBookInTheStorageDto) {
        return libraryService.changeBookStatus(id, putBookInTheStorageDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookById(@PathVariable Long id) {
        libraryService.deleteBookById(id);
    }

}

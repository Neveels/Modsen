package org.blueliner.librarywebservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Create a new book",
            description = "Create a new user book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Book successfully created",
                    content = @Content(schema = @Schema(implementation = BookResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest body) {
        return ResponseEntity
                .ok()
                .body(libraryService.createBook(body));
    }

    @Operation(
            summary = "Get all books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books",
                            content = @Content(schema = @Schema(implementation = BookResponse.class)))
            }
    )
    @GetMapping()
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity
                .ok()
                .body(libraryService.getAllBooks());
    }

    @Operation(
            summary = "Get a book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book found",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(libraryService.getBookById(id));
    }

    @Operation(
            summary = "Get books by ISBN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books",
                            content = @Content(schema = @Schema(implementation = BookResponse.class)))
            }
    )
    @GetMapping("/isnb/{isbn}")
    public ResponseEntity<List<BookResponse>> getAllBooksByISBN(@PathVariable String isbn) {
        return ResponseEntity
                .ok()
                .body(libraryService.getAllBooksByISBN(isbn));
    }

    @Operation(
            summary = "Get free books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of books",
                            content = @Content(schema = @Schema(implementation = BookResponse.class)))
            }
    )
    @GetMapping("/free")
    public ResponseEntity<List<BookResponse>> getFreeBooks() {
        return ResponseEntity
                .ok()
                .body(libraryService.getFreeBooks());
    }

    @Operation(
            summary = "Update a book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book updated",
                            content = @Content(schema = @Schema(implementation = BookResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @RequestBody BookRequest body) {
        return ResponseEntity
                .ok()
                .body(libraryService.updateBook(id, body));
    }

    @Operation(
            summary = "Update book status by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book status updated",
                            content = @Content(schema = @Schema(implementation = BookLogResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<BookLogResponse> updateBookStatus(@PathVariable Long id,
                                                            @RequestBody PutBookInTheStorageDto putBookInTheStorageDto) {
        return libraryService.changeBookStatus(id, putBookInTheStorageDto);
    }

    @Operation(
            summary = "Delete a book by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book deleted"),
                    @ApiResponse(responseCode = "404", description = "Book not found")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookById(@PathVariable Long id) {
        libraryService.deleteBookById(id);
    }

}

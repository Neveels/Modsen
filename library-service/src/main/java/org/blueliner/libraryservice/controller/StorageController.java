package org.blueliner.libraryservice.controller;

import lombok.RequiredArgsConstructor;
import org.blueliner.libraryservice.dto.PutBookInTheStorageDto;
import org.blueliner.libraryservice.service.BookWorker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage")
public class StorageController {

    private final BookWorker bookWorker;

    @GetMapping("/free")
    public ResponseEntity<Object> getFreeBooks() {
        return ResponseEntity
                .ok()
                .body(bookWorker.getFreeBooks());
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBookLog(@PathVariable Long bookId,
                              @RequestBody PutBookInTheStorageDto putBookInTheStorageDto
    ) {
        bookWorker.updateBookInTheStorage(bookId, putBookInTheStorageDto);
    }
}

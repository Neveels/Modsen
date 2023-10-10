package org.blueliner.libraryservice.controller;

import lombok.RequiredArgsConstructor;
import org.blueliner.libraryservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.libraryservice.dto.response.BookLogResponse;
import org.blueliner.libraryservice.dto.response.GetFreeBookResponse;
import org.blueliner.libraryservice.service.BookWorker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage")
public class StorageController {

    private final BookWorker bookWorker;

    @GetMapping("/free")
    public ResponseEntity<List<GetFreeBookResponse>> getFreeBooks() {
        return ResponseEntity
                .ok()
                .body(bookWorker.getFreeBooks());
    }

    @PutMapping("status/{bookId}")
    public ResponseEntity<BookLogResponse> updateBookLog(@PathVariable Long bookId,
                                                         @RequestBody PutBookInTheStorageDto putBookInTheStorageDto
    ) {
        return ResponseEntity
                .ok()
                .body(bookWorker.updateBookInTheStorage(bookId, putBookInTheStorageDto));
    }

}

package org.blueliner.libraryservice.service;

import org.blueliner.libraryservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.libraryservice.dto.response.BookLogResponse;
import org.blueliner.libraryservice.dto.response.GetFreeBookResponse;

import java.util.List;

public interface BookWorker {
    void saveNewBook(Long id);

    List<GetFreeBookResponse> getFreeBooks();

    BookLogResponse updateBookInTheStorage(Long bookId, PutBookInTheStorageDto putBookInTheStorageDto);
}

package org.blueliner.libraryservice.service;

import org.blueliner.libraryservice.dto.GetFreeBookResponse;
import org.blueliner.libraryservice.dto.PutBookInTheStorageDto;

import java.util.List;

public interface BookWorker {
    void saveNewBook(Long id);

    List<GetFreeBookResponse> getFreeBooks();

    void updateBookInTheStorage(Long bookId, PutBookInTheStorageDto putBookInTheStorageDto);
}

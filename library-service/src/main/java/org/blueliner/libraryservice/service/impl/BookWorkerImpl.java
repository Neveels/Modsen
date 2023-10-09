package org.blueliner.libraryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blueliner.libraryservice.dto.GetFreeBookResponse;
import org.blueliner.libraryservice.dto.PutBookInTheStorageDto;
import org.blueliner.libraryservice.exception.type.BookLogNotFoundException;
import org.blueliner.libraryservice.model.BookLog;
import org.blueliner.libraryservice.repository.BookLogRepository;
import org.blueliner.libraryservice.service.BookWorker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookWorkerImpl implements BookWorker {
    private final String BookLogNotFoundException = "Book with id %s not found";
    private final BookLogRepository bookLogRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveNewBook(Long id) {
        BookLog newLog = modelMapper.map(id, BookLog.class);
        bookLogRepository.save(newLog);
    }

    @Override
    public List<GetFreeBookResponse> getFreeBooks() {
        return bookLogRepository.findAll()
                .stream()
                .filter(bookLog -> bookLog.getStartTime() == null)
                .map(bookLog -> GetFreeBookResponse.builder()
                        .bookId(bookLog.getBookId())
                        .build()
                ).toList();
    }

    @Override
    public void updateBookInTheStorage(Long bookId, PutBookInTheStorageDto putBookInTheStorageDto) {
        BookLog bookLog = bookLogRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookLogNotFoundException(String.format(BookLogNotFoundException, bookId))
                );
        //FIXME: edit this logic
        if (!(putBookInTheStorageDto.start() == null)) {
            if (bookLog.getEndTime() != null) {

            }
        }
        if (!(putBookInTheStorageDto.end() == null)) {

        }
    }

}

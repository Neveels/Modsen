package org.blueliner.libraryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blueliner.libraryservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.libraryservice.dto.response.BookLogResponse;
import org.blueliner.libraryservice.dto.response.GetFreeBookResponse;
import org.blueliner.libraryservice.exception.type.BookHasAlreadyTakenException;
import org.blueliner.libraryservice.exception.type.BookLogNotFoundException;
import org.blueliner.libraryservice.model.BookLog;
import org.blueliner.libraryservice.repository.BookLogRepository;
import org.blueliner.libraryservice.service.BookWorker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookWorkerImpl implements BookWorker {
    private final String BOOK_LOG_NOT_FOUND_EXCEPTION = "Book with id %s not found";
    private final String BOOK_HAS_ALREADY_TAKEN_EXCEPTION = "Book has already taken exception";
    private final BookLogRepository bookLogRepository;

    @Override
    public void saveNewBook(Long id) {
        BookLog newLog = BookLog.builder().bookId(id).build();
        bookLogRepository.save(newLog);
    }

    @Override
    public List<GetFreeBookResponse> getFreeBooks() {
        return bookLogRepository.findAll()
                .stream()
                .filter(bookLog -> Objects.nonNull(bookLog.getStartTime()) && Objects.isNull(bookLog.getEndTime()))
                .map(bookLog -> GetFreeBookResponse.builder()
                        .bookId(bookLog.getBookId())
                        .build()
                )
                .toList();
    }

    @Override
    public BookLogResponse updateBookInTheStorage(Long bookId, PutBookInTheStorageDto putBookInTheStorageDto) {
        BookLog bookLog = bookLogRepository.findBookLogByBookId(bookId)
                .orElseThrow(() ->
                        new BookLogNotFoundException(String.format(BOOK_LOG_NOT_FOUND_EXCEPTION, bookId))
                );
        BookLog returnBookLog = null;

        if (Objects.nonNull(putBookInTheStorageDto.start())) {
            if (Objects.nonNull(bookLog.getStartTime()) && Objects.nonNull(bookLog.getEndTime())) {
                bookLog.setEndTime(null);
                bookLog.setStartTime(putBookInTheStorageDto.start());
                returnBookLog = bookLogRepository.save(bookLog);
            } else if (Objects.isNull(bookLog.getStartTime()) && Objects.isNull(bookLog.getEndTime())) {
                bookLog.setStartTime(putBookInTheStorageDto.start());
                returnBookLog = bookLogRepository.save(bookLog);
            } else {
                throw new BookHasAlreadyTakenException(BOOK_HAS_ALREADY_TAKEN_EXCEPTION);
            }
        } else if (Objects.nonNull(putBookInTheStorageDto.end())) {
            bookLog.setEndTime(putBookInTheStorageDto.end());
            returnBookLog = bookLogRepository.save(bookLog);
        }
        return BookLogResponse.builder()
                .id(returnBookLog.getBookId())
                .end(returnBookLog.getEndTime())
                .start(returnBookLog.getStartTime())
                .build();
    }

}


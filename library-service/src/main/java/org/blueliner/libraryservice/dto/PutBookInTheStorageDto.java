package org.blueliner.libraryservice.dto;

import java.time.LocalDateTime;

public record PutBookInTheStorageDto(
    LocalDateTime start,
    LocalDateTime end
) {
}

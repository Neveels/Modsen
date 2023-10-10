package org.blueliner.libraryservice.dto.request;

import java.time.LocalDate;

public record PutBookInTheStorageDto(
    LocalDate start,
    LocalDate end
) {
}

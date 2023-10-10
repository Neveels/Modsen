package org.blueliner.librarywebservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PutBookInTheStorageDto (
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate start,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate end
) {
}

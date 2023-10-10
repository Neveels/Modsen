package org.blueliner.librarywebservice.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookLogResponse(
    Long id,
    LocalDate start,
    LocalDate end
) {
}

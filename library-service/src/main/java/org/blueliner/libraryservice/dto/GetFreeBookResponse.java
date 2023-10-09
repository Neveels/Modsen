package org.blueliner.libraryservice.dto;

import lombok.Builder;

@Builder
public record GetFreeBookResponse(
        Long bookId
) {
}

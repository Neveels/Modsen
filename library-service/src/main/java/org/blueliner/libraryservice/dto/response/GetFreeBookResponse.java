package org.blueliner.libraryservice.dto.response;

import lombok.Builder;

@Builder
public record GetFreeBookResponse(
        Long bookId
) {
}

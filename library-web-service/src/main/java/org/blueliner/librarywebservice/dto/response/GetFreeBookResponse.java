package org.blueliner.librarywebservice.dto.response;

import lombok.Builder;
@Builder
public record GetFreeBookResponse (
    Long bookId
) {
}

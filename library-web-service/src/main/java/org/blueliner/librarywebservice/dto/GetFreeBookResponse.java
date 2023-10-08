package org.blueliner.librarywebservice.dto;

import lombok.Builder;
@Builder
public record GetFreeBookResponse (
        Long bookId
) {
}

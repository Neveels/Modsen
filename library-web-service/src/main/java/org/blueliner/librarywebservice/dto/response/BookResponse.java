package org.blueliner.librarywebservice.dto.response;

import lombok.*;
import org.blueliner.librarywebservice.model.Genre;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class BookResponse {
    private Long id;
    private String name;
    private Genre genre;
    private String description;
    private String authorName;
    private String isbn;

}

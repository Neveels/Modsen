package org.blueliner.librarywebservice.dto.request;

import lombok.*;
import org.blueliner.librarywebservice.model.Genre;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private String name;
    private Genre genre;
    private String description;
    private String authorName;
    private String isbn;
}

package org.blueliner.librarywebservice.kafka.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record KafkaBookIdAdditionDto (
        Long bookId
) {
}

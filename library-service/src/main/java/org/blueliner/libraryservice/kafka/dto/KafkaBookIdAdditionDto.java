package org.blueliner.libraryservice.kafka.dto;

import lombok.Builder;

@Builder
public record KafkaBookIdAdditionDto(
    Long bookId
) {
}

package org.blueliner.libraryservice.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.blueliner.libraryservice.kafka.dto.KafkaBookIdAdditionDto;
import org.blueliner.libraryservice.service.BookWorker;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Consumer {
    private final ObjectMapper objectMapper;
    private final BookWorker bookWorker;

    @SneakyThrows
    @KafkaListener(topics = "${topic.name}", groupId = "${topic-group.name")
    public void consumeMessage(String message) {
        KafkaBookIdAdditionDto kafkaMailSenderMessage = objectMapper.readValue(message, KafkaBookIdAdditionDto.class);
        bookWorker.saveNewBook(kafkaMailSenderMessage.bookId());
    }

}
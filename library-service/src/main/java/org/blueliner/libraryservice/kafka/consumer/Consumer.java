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
    @KafkaListener(topics = "book.addition.id.notification", groupId = "book_addition-group")
    public void consumeMessage(String message) {
        KafkaBookIdAdditionDto kafkaMailSenderMessage = objectMapper.readValue(message, KafkaBookIdAdditionDto.class);
        try {
            bookWorker.saveNewBook(kafkaMailSenderMessage.getId());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
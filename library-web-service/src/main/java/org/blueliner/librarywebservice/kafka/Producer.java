package org.blueliner.librarywebservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.blueliner.librarywebservice.kafka.dto.KafkaBookIdAdditionDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private final KafkaTemplate<String, KafkaBookIdAdditionDto> objectKafkaBookIdAdditionDtoTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendKafkaBookIdAdditionDtoToConsumer(Long id) {
        KafkaBookIdAdditionDto kafkaChangeStatusDto  = KafkaBookIdAdditionDto.builder()
                .bookId(id)
                .build();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String bookId = objectMapper.writeValueAsString(kafkaChangeStatusDto);
        objectKafkaBookIdAdditionDtoTemplate.send("book.addition.id.notification", kafkaChangeStatusDto);
        log.info("send book addition id notification to consumer {}", bookId);
    }
}

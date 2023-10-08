package org.blueliner.librarywebservice.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.blueliner.librarywebservice.kafka.dto.KafkaBookIdAdditionDto;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, KafkaBookIdAdditionDto> producerFactoryForObjectKafkaBookIdAdditionDtoTemplate() {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, KafkaBookIdAdditionDto> objectKafkaBookIdAdditionDtoTemplate() {
        return new KafkaTemplate<>(producerFactoryForObjectKafkaBookIdAdditionDtoTemplate());
    }

    @Bean
    public NewTopic topicSendChangeStatusVieEmailEvent() {
        return TopicBuilder
                .name("book.addition.id.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

}


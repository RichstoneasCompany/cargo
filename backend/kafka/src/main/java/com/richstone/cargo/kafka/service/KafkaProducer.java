package com.richstone.cargo.kafka.service;

import com.richstone.cargo.kafka.dto.TemperatureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, TemperatureDto> kafkaTemplate;

    public void sendMessage(TemperatureDto temperatureDto) {

        Message<TemperatureDto> message = MessageBuilder
                .withPayload(temperatureDto)
                .setHeader(KafkaHeaders.TOPIC, "temperature-topic")
                .build();

        kafkaTemplate.send(message);
    }
}

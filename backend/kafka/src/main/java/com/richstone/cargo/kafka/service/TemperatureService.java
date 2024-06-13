package com.richstone.cargo.kafka.service;

import com.richstone.cargo.kafka.dto.TemperatureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureService {
   private final KafkaProducer kafkaProducer;
    private final Random random = new Random();

    @Scheduled(fixedRateString = "${temperature.data.generation.interval}")
    public void generateTemperatureData() {
        double temperature = 20 + random.nextGaussian() * 2;

        if (random.nextInt(100) < 5) {
            temperature = 25 + random.nextGaussian() * 2;
        }
        TemperatureDto temperatureDto = TemperatureDto.builder()
                .userId(2L)
                .temperature(temperature)
                .timestamp(LocalDateTime.now())
                .build();
       kafkaProducer.sendMessage(temperatureDto);

    }
}

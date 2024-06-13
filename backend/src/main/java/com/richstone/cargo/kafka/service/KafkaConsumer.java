package com.richstone.cargo.kafka.service;

import com.richstone.cargo.kafka.dto.TemperatureDto;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.FirebaseNotificationServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final FirebaseNotificationServiceImpl notificationService;
    private final UserServiceImpl userService;

    @KafkaListener(topics = "temperature-topic", groupId = "temperature-group")
    public void consumeJsonMsg(TemperatureDto temperatureDto) {
        log.info(format("Consuming the message from Topic:: %s", temperatureDto.toString()));
        if(temperatureDto.getTemperature()>22){
            User user = userService.findById(temperatureDto.getUserId());
            notificationService.sendTemperatureNotification(user);
        }
    }

}

package com.richstone.cargo.service.impl;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.richstone.cargo.dto.NotificationMessage;
import com.richstone.cargo.service.FirebaseNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseNotificationServiceImpl implements FirebaseNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build()).build();
    }
    public void sendNotification(NotificationMessage message) {
        try {
            Message preconfiguredMessage = getPreconfiguredMessage(message);
            String jsonOutput = new Gson().toJson(message);
            String response = sendAndGetResponse(preconfiguredMessage);
            log.info("Sent message. Topic: {}, Response: {}, Message: {}", message.getTopic(), response, jsonOutput);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error sending notification", e);
        }
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return firebaseMessaging.sendAsync(message).get();
    }

    private Message getPreconfiguredMessage(NotificationMessage notificationMessage) {
        AndroidConfig androidConfig = getAndroidConfig(notificationMessage.getTopic());
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getMessage())
                .build();

        return Message
                .builder()
                .setAndroidConfig(androidConfig)
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .build();
    }

    public void sendTripUpdateNotification(NotificationMessage message){


    }
}

package com.richstone.cargo.service;

import com.richstone.cargo.dto.NotificationMessage;

public interface FirebaseNotificationService {

    void sendNotification(NotificationMessage message);

}

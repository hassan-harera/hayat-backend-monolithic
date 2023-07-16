package com.harera.hayat.service;

import org.springframework.stereotype.Service;

import com.harera.hayat.model.notificaiton.Notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationsConsumer {

    private final FirebaseService firebaseService;
    public NotificationsConsumer(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    public void consumeNotifications(Notification notification) {
        firebaseService.send(notification);
        log.debug("Notification Sent: " + notification);
    }
}

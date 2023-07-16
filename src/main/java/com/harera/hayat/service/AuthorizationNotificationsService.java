package com.harera.hayat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.notificaiton.Notification;
import com.harera.hayat.model.user.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AuthorizationNotificationsService {


    public void notifyNewLoginDetected(User user) {
        Notification notification = createNewLoginNotification(user);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private void sendNotification(Notification notification) {
    }

    private Notification createNewLoginNotification(User user) {
        Notification notification = new Notification();
        notification.setTitle("New login detected");
        notification.setBody("We detected a new login to your account");
        notification.setDeviceToken(user.getDeviceToken());
        notification.setUserId(user.getId());
        return notification;
    }
}

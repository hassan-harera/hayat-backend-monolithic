package com.harera.hayat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.Need;
import com.harera.hayat.model.notificaiton.Notification;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class NeedNotificationsService {

    private final String notificationsQueue;

    public NeedNotificationsService(
                    @Value("${spring.rabbitmq.queue.notifications}") String notificationsQueue) {
        this.notificationsQueue = notificationsQueue;
    }

    public void notifyProcessingNeed(Need need) {
        Notification notification = createProcessingDonationNotification(need);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createProcessingDonationNotification(Need donation) {
        Notification notification = new Notification();
        notification.setTitle("Need post is under review");
        notification.setBody("We are reviewing your need post, please wait");
        notification.setDeviceToken(donation.getUser().getDeviceToken());
        notification.setUserId(donation.getUser().getId());
        return notification;
    }

    private void sendNotification(Notification notification) {
    }

    public void notifyProcessingDonation(Need donation) {
        Notification notification = createProcessingDonationNotification(donation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    public void notifyDonationAccepted(Need bookDonation) {
        Notification notification = createDonationAcceptedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationAcceptedNotification(Need bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is accepted");
        notification.setBody("Your donation is accepted, people can now request it");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }

    public void notifyDonationRejected(Need bookDonation) {
        Notification notification = createDonationRejectedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationRejectedNotification(Need bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is rejected");
        notification.setBody("Your donation is rejected, please check your donation");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }
}

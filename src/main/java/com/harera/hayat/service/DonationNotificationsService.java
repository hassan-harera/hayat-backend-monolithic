package com.harera.hayat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.harera.hayat.model.BaseDonation;
import com.harera.hayat.model.notificaiton.Notification;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DonationNotificationsService {


    public void notifyProcessingDonation(BaseDonation donation) {
        Notification notification = createProcessingDonationNotification(donation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private void sendNotification(Notification notification) {
    }

    private Notification createProcessingDonationNotification(BaseDonation donation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is under review");
        notification.setBody("We are reviewing your donation, please wait");
        notification.setDeviceToken(donation.getUser().getDeviceToken());
        notification.setUserId(donation.getUser().getId());
        return notification;
    }

    public void notifyDonationAccepted(BaseDonation bookDonation) {
        Notification notification = createDonationAcceptedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationAcceptedNotification(BaseDonation bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is accepted");
        notification.setBody("Your donation is accepted, people can now request it");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }

    public void notifyDonationRejected(BaseDonation bookDonation) {
        Notification notification = createDonationRejectedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationRejectedNotification(BaseDonation bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is rejected");
        notification.setBody("Your donation is rejected, please check your donation");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }
}

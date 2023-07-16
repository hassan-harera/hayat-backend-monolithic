package com.harera.hayat.service.announcement;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> list() {
        return announcementRepository.findAllByActive(true);
    }
}

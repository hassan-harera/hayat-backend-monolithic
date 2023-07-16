package com.harera.hayat.service.announcement;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

    @Query("{active: {$eq: ?0}}")
    List<Announcement> findAllByActive(boolean active);
}

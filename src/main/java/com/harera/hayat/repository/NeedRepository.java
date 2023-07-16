package com.harera.hayat.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.Need;

@Repository
public class NeedRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public NeedRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Need> search(String query, int page) {
        return List.of();
    }
}

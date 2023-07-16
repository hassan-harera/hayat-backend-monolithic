package com.harera.hayat.repository.possession;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.harera.hayat.model.possession.PossessionNeed;

public interface PossessionNeedRepository
                extends MongoRepository<PossessionNeed, String> {
}

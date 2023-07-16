package com.harera.hayat.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDocument {

    @Id
    private String id;

    @Field(name = "active")
    private boolean active = true;

    @Indexed(name = "created_at", expireAfterSeconds = 1296000)
    @Field(name = "created_at")
    private long createdAt = System.currentTimeMillis();
}

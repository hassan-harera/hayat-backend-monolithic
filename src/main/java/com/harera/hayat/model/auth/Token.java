package com.harera.hayat.model.auth;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash(value = "token", timeToLive = 1800)
public class Token implements Serializable {

    private String id;
    private Long userId;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Token(String id, Long userId) {
        this.userId = userId;
        this.id = id;
    }
}

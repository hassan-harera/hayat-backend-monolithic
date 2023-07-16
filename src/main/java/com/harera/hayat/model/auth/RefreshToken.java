package com.harera.hayat.model.auth;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash(value = "token", timeToLive = 1800)
public class RefreshToken implements Serializable {

    private String refreshToken;
    private Long id;
    private LocalDateTime timestamp = LocalDateTime.now();

    public RefreshToken(String refreshToken, Long id) {
        this.refreshToken = refreshToken;
        this.id = id;
    }
}

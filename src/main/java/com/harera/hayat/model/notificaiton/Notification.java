package com.harera.hayat.model.notificaiton;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification implements Serializable {

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("device_token")
    private String deviceToken;
}

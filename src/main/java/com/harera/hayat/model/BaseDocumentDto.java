package com.harera.hayat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDocumentDto {

    @Id
    private String id;

    @JsonProperty(value = "active")
    private boolean active = true;
}

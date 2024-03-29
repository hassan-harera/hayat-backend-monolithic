package com.harera.hayat.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.model.city.CityDto;
import com.harera.hayat.model.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Need extends BaseDocument {

    @Field(name = "title")
    private String title;

    @Field(name = "description")
    private String description;

    @Field(name = "need_date")
    private LocalDateTime needDate;

    @Field(name = "need_expiration_date")
    private LocalDateTime needExpirationDate;

    @Field(name = "category")
    private NeedCategory category;

    @Field(name = "status")
    private NeedStatus status;

    @Field(name = "communication_method")
    private CommunicationMethod communicationMethod;

    @Field(name = "image_url")
    private String imageUrl;

    @Field(name = "city")
    private CityDto city;

    @Field(name = "user")
    private UserDto user;

    @Field(name = "qr_code")
    private String qrCode = UUID.randomUUID().toString();

    @Field(name = "upvotes")
    private Set<Long> upvotes = new HashSet<>();

    @Field(name = "downvotes")
    private Set<Long> downvotes = new HashSet<>();

    public Integer getReputation() {
        return upvotes.size() - downvotes.size();
    }

    public void upvote(Long userId) {
        upvotes.add(userId);
        downvotes.remove(userId);
    }

    public void downvote(Long userId) {
        downvotes.add(userId);
        upvotes.remove(userId);
    }
}

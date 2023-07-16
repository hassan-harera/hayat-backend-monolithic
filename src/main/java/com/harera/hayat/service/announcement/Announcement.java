package com.harera.hayat.service.announcement;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.harera.hayat.model.BaseDocument;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "announcements")
public class Announcement extends BaseDocument {

    @Field(name = "title")
    public String title;

    @Field(name = "description")
    public String description;

    @Field(name = "image_url")
    public String imageUrl;
}

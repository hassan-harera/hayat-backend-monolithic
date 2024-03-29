package com.harera.hayat.model.book;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.model.DonationDto;

import lombok.Data;

@Data
public class BookDonationDto extends DonationDto {

    @JsonProperty(value = "quantity")
    private Integer quantity;

    @JsonProperty(value = "book_title")
    private String bookTitle;

    @JsonProperty(value = "book_sub_title")
    private String bookSubTitle;

    @JsonProperty(value = "book_author")
    private String bookAuthor;

    @JsonProperty(value = "book_publisher")
    private String bookPublisher;

    @JsonProperty(value = "book_publication_year")
    private LocalDate bookPublicationYear;

    @JsonProperty(value = "book_language")
    private String bookLanguage;
}

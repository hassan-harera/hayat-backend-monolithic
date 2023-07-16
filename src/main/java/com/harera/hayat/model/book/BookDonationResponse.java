package com.harera.hayat.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user_id", "city_id" })
public class BookDonationResponse extends BookDonationDto {

}

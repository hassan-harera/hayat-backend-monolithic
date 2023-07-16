package com.harera.hayat.service.book;

import org.springframework.stereotype.Service;

import com.harera.hayat.model.book.BookNeedRequest;
import com.harera.hayat.repository.city.CityRepository;
import com.harera.hayat.service.NeedValidation;

@Service
public class BookNeedValidation extends NeedValidation {

    public BookNeedValidation(CityRepository cityRepository) {
        super(cityRepository);
    }

    public void validate(BookNeedRequest bookNeedRequest) {
        super.validate(bookNeedRequest);
    }
}

package com.harera.hayat.service.book;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.MandatoryFieldException;
import com.harera.hayat.util.ErrorCode;
import com.harera.hayat.model.book.BookDonationRequest;
import com.harera.hayat.service.DonationValidation;

@Service
public class BookDonationValidation {

    private final DonationValidation donationValidation;

    public BookDonationValidation(DonationValidation donationValidation) {
        this.donationValidation = donationValidation;
    }

    public void validateCreate(BookDonationRequest request) {
        donationValidation.validateCreate(request);
        validateMandatory(request);
        validateFormat(request);
        // TODO: validate other not null values (e.g. book status, book sub title)
    }

    private void validateFormat(BookDonationRequest request) {
        if (request.getBookTitle().length() < 4
                        || request.getBookTitle().length() > 100) {
            throw new MandatoryFieldException(ErrorCode.FORMAT_BOOK_DONATION_TITLE,
                            "book_title");
        }
        if (request.getQuantity() < 1 || request.getQuantity() > 1000) {
            throw new MandatoryFieldException(ErrorCode.FORMAT_BOOK_DONATION_AMOUNT,
                            "quantity");
        }
    }

    private void validateMandatory(BookDonationRequest request) {
        if (request.getBookTitle() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_BOOK_DONATION_TITLE,
                            "book_title");
        }
        if (request.getQuantity() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_BOOK_DONATION_AMOUNT,
                            "quantity");
        }
    }
}

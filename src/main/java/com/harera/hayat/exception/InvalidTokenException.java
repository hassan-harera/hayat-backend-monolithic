package com.harera.hayat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseBody
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {

    private final String code;

    public InvalidTokenException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "InvalidTokenException{" + "code='" + code + '\'' + '}';
    }
}

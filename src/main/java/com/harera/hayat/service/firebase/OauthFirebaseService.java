package com.harera.hayat.service.firebase;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.exception.InvalidTokenException;
import com.harera.hayat.util.ErrorCode;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OauthFirebaseService {

    private final FirebaseAuth firebaseAuth;
    private final ModelMapper modelMapper;

    protected OauthFirebaseService(FirebaseAuth firebaseAuth, ModelMapper modelMapper) {
        this.firebaseAuth = firebaseAuth;
        this.modelMapper = modelMapper;
    }

    public FirebaseToken getFirebaseToken(String token) {
        if (isBlank(token))
            throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                            "Invalid Token");
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(token, true);
            if (firebaseToken == null)
                throw new InvalidTokenException(ErrorCode.INVALID_FIREBASE_TOKEN,
                                "Invalid Token");
            return firebaseToken;
        } catch (FirebaseAuthException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
}

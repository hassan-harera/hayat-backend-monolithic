package com.harera.hayat.service.oauth;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseToken;
import com.harera.hayat.model.auth.Token;
import com.harera.hayat.model.auth.LoginResponse;
import com.harera.hayat.model.auth.SignupResponse;
import com.harera.hayat.model.oauth.OauthLoginRequest;
import com.harera.hayat.model.oauth.OauthSignupRequest;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.TokenRepository;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.AuthorizationNotificationsService;
import com.harera.hayat.service.firebase.OauthFirebaseService;
import com.harera.hayat.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OauthValidation oauthValidation;
    private final OauthFirebaseService oauthFirebaseService;
    private final ModelMapper modelMapper;
    private final AuthorizationNotificationsService notificationsService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public LoginResponse login(OauthLoginRequest oAuthLoginRequest) {
        oauthValidation.validateLogin(oAuthLoginRequest);

        FirebaseToken firebaseToken = oauthFirebaseService
                        .getFirebaseToken(oAuthLoginRequest.getFirebaseToken());
        User user = userRepository.findByUid(firebaseToken.getUid()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));

        notificationsService.notifyNewLoginDetected(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(user));
        loginResponse.setRefreshToken(jwtService.generateRefreshToken(user));

        tokenRepository.save(new Token(loginResponse.getToken(), user.getId()));

        return loginResponse;
    }

    public SignupResponse signup(OauthSignupRequest signupRequest) {
        oauthValidation.validateSignup(signupRequest);

        FirebaseToken firebaseUser = oauthFirebaseService
                        .getFirebaseToken(signupRequest.getOauthToken());

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUid(firebaseUser.getUid());
        user.setUsername(firebaseUser.getUid());

        userRepository.save(user);

        return modelMapper.map(user, SignupResponse.class);
    }
}

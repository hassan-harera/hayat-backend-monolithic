package com.harera.hayat.service.auth;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.auth.LoginRequest;
import com.harera.hayat.model.auth.LoginResponse;
import com.harera.hayat.model.auth.LogoutRequest;
import com.harera.hayat.model.auth.RefreshTokenRequest;
import com.harera.hayat.model.auth.ResetPasswordRequest;
import com.harera.hayat.model.auth.SignupRequest;
import com.harera.hayat.model.auth.SignupResponse;
import com.harera.hayat.model.auth.Token;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.TokenRepository;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.AuthorizationNotificationsService;
import com.harera.hayat.service.UserUtils;
import com.harera.hayat.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidation authValidation;
    private final ModelMapper modelMapper;
    private final UserUtils userUtils;
    private final JwtService jwtService;
    private final AuthorizationNotificationsService notificationsService;
    private final TokenRepository tokenRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        authValidation.validateLogin(loginRequest);

        long userId = userUtils.getUserId(loginRequest.getSubject());
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));

        if (isNotEmpty(loginRequest.getDeviceToken())) {
            user.setDeviceToken(loginRequest.getDeviceToken());
            userRepository.save(user);
        }
        notificationsService.notifyNewLoginDetected(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(user));
        loginResponse.setRefreshToken(jwtService.generateRefreshToken(user));

        tokenRepository.save(new Token(loginResponse.getToken(), user.getId()));

        return loginResponse;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        authValidation.validateSignup(signupRequest);

        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUsername(user.getMobile());

        userRepository.save(user);

        return modelMapper.map(user, SignupResponse.class);
    }

    public void logout(LogoutRequest logoutRequest) {
    }

    public LoginResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        LoginResponse authResponse = new LoginResponse();
        // TODO
        return authResponse;
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        authValidation.validateResetPassword(resetPasswordRequest);

        long userId = userUtils.getUserId(resetPasswordRequest.getMobile());
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new EntityNotFoundException(User.class, userId));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

        userRepository.save(user);
    }
}

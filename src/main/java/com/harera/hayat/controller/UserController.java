package com.harera.hayat.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.jwt.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserRepository userRepository;
    private final JwtService jwtUtils;

    public UserController(UserRepository userRepository, JwtService jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/me")
    public ResponseEntity<User> get(
                    @RequestHeader("Authorization") String authorizationHeader) {
        String subject = jwtUtils.extractUserSubject(authorizationHeader.substring(7));
        return ok(userRepository.findByUsername(subject)
                        .orElseThrow(() -> new RuntimeException("User not found")));
    }
}

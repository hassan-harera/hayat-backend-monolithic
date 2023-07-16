package com.harera.hayat.config;

import static com.google.firebase.FirebaseApp.getApps;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void checkFirebaseApps() {
        if (getApps().isEmpty()) {
            initialize();
        }
    }

    private void initialize() {
        try {
            InputStream inputStream =
                            new ClassPathResource("hayateg.json").getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(inputStream))
                            .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error("Error initializing Firebase", e);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}

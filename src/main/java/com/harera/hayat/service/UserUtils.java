package com.harera.hayat.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.harera.hayat.util.RegexUtils.isEmail;
import static com.harera.hayat.util.RegexUtils.isPhoneNumber;
import static com.harera.hayat.util.RegexUtils.isUsername;

@Service
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    public long getUserId(String subject) throws EntityNotFoundException {
        User user = getUser(subject);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        } else {
            return user.getId();
        }
    }

    public User getUser(String subject) {
        Optional<User> user = Optional.empty();
        if (isPhoneNumber(subject)) {
            user = userRepository.findByMobile(subject);
        } else if (isEmail(subject)) {
            user = userRepository.findByEmail(subject);
        } else if (isUsername(subject)) {
            user = userRepository.findByUsername(subject);
        }
        return user.orElse(null);
    }
}

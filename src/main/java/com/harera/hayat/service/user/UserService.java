package com.harera.hayat.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.exception.InvalidTokenException;
import com.harera.hayat.model.user.User;
import com.harera.hayat.model.user.UserResponse;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.jwt.JwtService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtUtils;

    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                       JwtService jwtUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
    }

    public UserResponse get(long id) {
        return userRepository.findById(id)
                        .map(user -> modelMapper.map(user, UserResponse.class))
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        long id = Long.parseLong(username);
        return userRepository.findById(id).orElseThrow(
                        () -> new UsernameNotFoundException("User not found"));
    }

    public User getUser(String authorization) {
        String subject = jwtUtils.extractUserSubject(authorization);
        return userRepository.findByUsername(subject).orElseThrow(
                        () -> new InvalidTokenException("", "Invalid token"));
    }
}

package com.harera.hayat.service.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.harera.hayat.exception.EntityNotFoundException;
import com.harera.hayat.model.user.Role;
import com.harera.hayat.model.user.User;
import com.harera.hayat.repository.TokenRepository;
import com.harera.hayat.repository.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final String tokenExpire;
    private final String refreshTokenExpire;
    private final String secretKey;
    private final UserRepository userRepository;

    @Autowired
    public JwtService(@Value("${jwt.token.expire}") String tokenExpire,
                    @Value("${jwt.token.refresh.expire}") String refreshTokenExpire,
                    @Value("${jwt.token.secret.key}") String secretKey,
                    UserRepository userRepository, TokenRepository tokenRepository) {
        this.tokenExpire = tokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.secretKey = secretKey;
        this.userRepository = userRepository;
    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("uid", user.getUid());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("mobile", user.getMobile());
        claims.put("role", Role.GUEST);
        claims.put("authorities", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toArray());
        return claims;
    }

    public String generateToken(User user) {
        final String userSubject = String.valueOf(user.getId());
        final String token = createToken(userSubject, Long.valueOf(tokenExpire),
                        getClaims(user));
        return token;
    }

    public String generateRefreshToken(User user) {
        final String token = createToken(user.getId().toString(),
                        Long.valueOf(refreshTokenExpire), null);
        return token;
    }

    private String createToken(String username, long expireInMillis,
                    Map<String, Object> claims) {
        final JwtBuilder jwtBuilder = Jwts.builder();
        if (claims != null) {
            jwtBuilder.setClaims(claims);
        }

        if (expireInMillis != -1) {
            jwtBuilder.setExpiration(
                            new Date(System.currentTimeMillis() + expireInMillis));
        }

        jwtBuilder.setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                        .signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

    public User getUser(String token) {
        long subject = Long.parseLong(extractUserSubject(token));
        return userRepository.findById(subject).orElseThrow(
                        () -> new EntityNotFoundException(User.class, subject, ""));
    }

    public String extractUserSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                        .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserSubject(token);
        return (username.equals(userDetails.getUsername()));
    }
}

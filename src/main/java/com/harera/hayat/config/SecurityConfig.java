package com.harera.hayat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private static final String[] OPEN_APIS = { "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security", "/swagger-ui/**",
            "/webjars/**", "/swagger-ui.html", "/api/v1/food/**", "/api/v1/cities/**",
            "/api/v1/medicines/**", "/api/v1/auth/**", "/api/v1/oauth/**",
            "/api/v1/auth/password-reset", "/api/v1/auth/logout", "/api/v1/otp/**",
            "/actuator/**", "/actuator/prometheus/**" };

    @Bean
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http)
                    throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
                        .formLogin(AbstractHttpConfigurer::disable)
                        .cors(AbstractHttpConfigurer::disable)
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(requests -> requests
                                        .requestMatchers(OPEN_APIS).permitAll()
                                        .anyRequest().authenticated())
                        .sessionManagement(session -> session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                        .logout().logoutUrl("/api/v1/auth/logout").and()
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtAuthFilter,
                                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                        new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

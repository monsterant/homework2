package com.example.homework2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/member/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/post/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/comment/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/post/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/post/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/post/**").authenticated()


                        .requestMatchers(HttpMethod.POST, "/comment/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/comment/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/comment/**").authenticated()

                        .requestMatchers("/authCustom/login").permitAll()
                        .requestMatchers("/authCustom/logout").authenticated()
                        .requestMatchers("/authCustom/me").authenticated()

                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

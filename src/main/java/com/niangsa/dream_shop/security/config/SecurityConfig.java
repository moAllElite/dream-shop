package com.niangsa.dream_shop.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenFilter authenFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        r->
                            r.requestMatchers(
                                    "/auth/**",
                                    "/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                            ).permitAll()

                )
                .authorizeHttpRequests(r-> r.anyRequest().authenticated())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

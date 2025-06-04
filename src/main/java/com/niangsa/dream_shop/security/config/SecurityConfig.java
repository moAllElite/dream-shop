package com.niangsa.dream_shop.security.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenFilter authenFilter;

    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> {
                        // Public Endpoints (Swagger, Auth)
                        auth.requestMatchers(
                                "/auth/**",
                                "/products",
                                "/products?size=**",
                                "/products/search-by/**",
                                "product/search/**",
                                "/api-docs.yaml",
                                "/images/image/download/**",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll();

                        // Any other request needs authentication
                       auth.anyRequest().authenticated();
                    })
                    .oauth2Client(Customizer.withDefaults())//for social authentification
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(authenFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        //cors configuration
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200","*"));
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setMaxAge(36000L);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }


}




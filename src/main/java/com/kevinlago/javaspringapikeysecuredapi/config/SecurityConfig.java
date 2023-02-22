package com.kevinlago.javaspringapikeysecuredapi.config;

import com.kevinlago.javaspringapikeysecuredapi.filter.ApiKeyAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Value("${env.http.security.api-key.header}")
    private String apiKeyHeader;
    @Value("${env.http.security.api-key.value}")
    private String apiKeyValue;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter apiKeyAuthFilter = new ApiKeyAuthFilter(apiKeyHeader);
        apiKeyAuthFilter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principle = (String) authentication.getPrincipal();

                if (!apiKeyValue.equals(principle)) {
                    throw new BadCredentialsException("Failed to authenticate");
                }

                authentication.setAuthenticated(true);
                return authentication;
            }
        });

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(apiKeyAuthFilter).authorizeHttpRequests().anyRequest().authenticated();

        return http.build();
    }
}

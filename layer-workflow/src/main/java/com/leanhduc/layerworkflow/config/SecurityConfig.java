package com.leanhduc.layerworkflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PreSecurityFilterChain preSecurityFilterChain;
    private final PostSecurityFilterChain postSecurityFilterChain;

    public SecurityConfig(PreSecurityFilterChain preSecurityFilterChain, PostSecurityFilterChain postSecurityFilterChain) {
        this.preSecurityFilterChain = preSecurityFilterChain;
        this.postSecurityFilterChain = postSecurityFilterChain;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/**").permitAll())
                // Define location filter to place custom filter
                .addFilterBefore(preSecurityFilterChain, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(postSecurityFilterChain, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

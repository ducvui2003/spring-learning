package com.leanhduc.springsecurityjwt.config;

import com.leanhduc.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JwtConfig {

    @Primary
    @Bean(name = "accessTokenUtil")
    public JwtUtil accessTokenUtil(
            @Value("${jwt.access_token.secret_key}") String secretKey,
            @Value("${jwt.access_token.expired}") long expired) {
        return new JwtUtil(secretKey, expired);
    }

    @Bean(name = "refreshTokenUtil")
    public JwtUtil refreshTokenUtil(
            @Value("${jwt.refresh_token.secret_key}") String secretKey,
            @Value("${jwt.refresh_token.expired}") long expired) {
        return new JwtUtil(secretKey, expired);
    }
}

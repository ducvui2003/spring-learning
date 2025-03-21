package com.leanhduc.springsecurityjwt.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private final String secretKey;
    private final long duration;
    private final SecretKey key;


    public JwtUtil(String secretKey, long duration) {
        this.secretKey = secretKey;
        this.duration = duration;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username) {
        Date current = new Date();
        Date expiration = new Date(current.getTime() + duration);
        Header header = Jwts.header().keyId(secretKey).build();
        String uuid = UUID.randomUUID().toString();
        return Jwts.builder()
                .header().add(header).and()
                .id(uuid)
                .issuer(username)
                .subject(username)
                .issuedAt(current)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        JwtUtil jwt = new JwtUtil("jwt-tokenjwt-tokenjwt-tokenjwt-tokenjwt-tokenjwt-token", 3600);
        String token = jwt.generateToken("jwt-user");
        System.out.println(token);
        System.out.println(jwt.validateToken(token));
    }
}

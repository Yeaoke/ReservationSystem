package com.example.app.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class JwtService {
    
    private final String MySecretKey = "mySecretKey";
    private final Long expirationMs = 86400000L;

    public String generateToken(String email) {
        return Jwts.builder()
            .compact();
    }

    public void extractUsername() {}
    public void extractPassword() {}
}

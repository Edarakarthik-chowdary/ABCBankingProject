package com.karthik.banking_backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =
            "ThisIsMySecretKeyForBankingApplicationJwtAuthentication123456789";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(StandardCharsets.UTF_8)
            );

    // Generate JWT Token
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)
                ) // 24 hours
                .signWith(key)
                .compact();
    }

    // Extract Username (Email) from Token
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    // Extract all Claims
    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {

        try {
            Date expiration = extractAllClaims(token).getExpiration();

            return expiration.before(new Date());

        } catch (JwtException | IllegalArgumentException e) {

            return true;
        }
    }

    // Validate Token
    public boolean validateToken(String token, String email) {

        try {

            String username = extractUsername(token);

            return username.equals(email)
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }
}
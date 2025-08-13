package com.lvrgese.news_aggregator.auth.util;

import com.lvrgese.news_aggregator.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final long EXPIRATION_TIME = 10 * 1000 * 60 * 60; //10-Hour
    private static final String SECRET_KEY = "my-256-bit-secret-my-256-bit-random-secret-my";

    public static SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateJwtToken(String username){

        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }

    public static boolean validateJwtToken(String token) throws InvalidJwtTokenException {
        try{
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token); //throws if invalid or expired
            return true;
        }
        catch (Exception ex){
            throw new InvalidJwtTokenException("Couldn't validate JWT token");
        }
    }

    public static String extractUserName(String token){
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }
        catch (Exception ex){
            throw new UsernameNotFoundException("Couldn't extract username");
        }
    }
}

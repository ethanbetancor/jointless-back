package com.example.demo.domain.service;

import com.example.demo.domain.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService<T> {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.email-expiration}")
    private long emailExpiration;


    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String getType(String token) {return getClaim(token, claims -> claims.get("type", String.class));}

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateEmailToken(User user) {
        return generateEmailToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public String generateEmailToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + emailExpiration))
                .claim("type", "EMAIL_VERIFICATION")
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateToken(String token, User user) {
        String username = getUsername(token);
        return (username.equals(user.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date(System.currentTimeMillis()));
    }


}

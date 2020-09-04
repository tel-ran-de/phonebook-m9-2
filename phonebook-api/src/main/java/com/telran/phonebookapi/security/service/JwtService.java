package com.telran.phonebookapi.security.service;

import com.telran.phonebookapi.security.model.JWToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${com.telran.auth.jwt.access-token.expiration}")
    int expirationMinutes;

    private static final String USERNAME_CLAIM = "username";
    private static final String AUTHORITIES_CLAIM = "authorities";

    @Value("${com.telran.auth.jwt.secret}")
    String jwtSecret;

    public String generateAccessToken(UserDetails user) {
        Date date = Date.from(ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant());
        Claims claims = new DefaultClaims();
        claims.put(USERNAME_CLAIM, user.getUsername());
        claims.put(AUTHORITIES_CLAIM, user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public JWToken parseToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();

        Object authorities = claims.get(AUTHORITIES_CLAIM);

        return JWToken.builder()
                .username(claims.get(USERNAME_CLAIM, String.class))
                .authorities((List<String>) authorities)
                .expirationTime(ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault()))
                .build();
    }

}

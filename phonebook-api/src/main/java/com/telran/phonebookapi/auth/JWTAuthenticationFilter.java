package com.telran.phonebookapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.phonebookapi.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            JwtProperties jwtProperties) {
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDto.email,
                    userDto.password
            );

            return authenticationManager.authenticate(auth);
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setExpiration(new Date(jwtProperties.getExpiration() * 1000))
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("authorities", authorities)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8))
                .compact();

        Cookie cookie = new Cookie("at", token);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }
}

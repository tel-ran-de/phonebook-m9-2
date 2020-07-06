package com.telran.phonebookapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.phonebookapi.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
import java.util.Date;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/api/user/login", "POST"));
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)
            throws AuthenticationException {
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

        String token = Jwts.builder()
                .setExpiration(new Date())
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .signWith(SignatureAlgorithm.HS512, "secret".getBytes(StandardCharsets.UTF_8))
                .compact();

        Cookie cookie = new Cookie("at", token);
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }
}

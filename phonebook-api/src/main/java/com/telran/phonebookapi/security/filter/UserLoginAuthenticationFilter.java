package com.telran.phonebookapi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.phonebookapi.dto.UserDto;
import com.telran.phonebookapi.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    public UserLoginAuthenticationFilter(
            String path,
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper, JwtService jwtService) {
        super(new AntPathRequestMatcher(path, "POST"));
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)
            throws AuthenticationException, IOException {
        UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDto.email,
                userDto.password
        );

        return authenticationManager.authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        String accessToken = jwtService.generateAccessToken(((UserDetails) auth.getPrincipal()));

//        Cookie cookie = new Cookie("at", accessToken);
//        cookie.setHttpOnly(true);
//        res.addCookie(cookie);

        res.addHeader("Access-Token", accessToken);
    }
}

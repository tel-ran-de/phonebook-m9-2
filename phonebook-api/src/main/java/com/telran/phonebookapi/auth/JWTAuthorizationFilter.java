package com.telran.phonebookapi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProperties jwtProperties;

    public JWTAuthorizationFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        if(req.getCookies() != null) {
            List<Cookie> cookie = Arrays.stream(req.getCookies())
                    .filter(x -> x.getName().equals("at"))
                    .collect(Collectors.toList());

            if (cookie.get(0) == null) {
                chain.doFilter(req, res);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(req, cookie);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req, List<Cookie> cookie) {
        String token = cookie.get(0).getValue();
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret().getBytes())
                    .parse(token).getBody().toString();

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret().getBytes())
                    .parseClaimsJws(token).getBody();

            if (user != null) {
                List<String> authorities = (List<String>) claims
                        .get("authorities");

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null,
                        authorities.stream().map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()));
                return auth;
            }
            return null;
        }
        return null;
    }
}

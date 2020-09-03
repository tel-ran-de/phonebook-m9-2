package com.telran.phonebookapi.security.model;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
public class JWToken {
    String username;
    List<String> authorities;
    ZonedDateTime expirationTime;
}

package com.telran.phonebookapi.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@Component
public class JwtProperties {

	@Value("${jwt.expiration}")
	private int expiration;

	@Value("${jwt.secret}")
	private String secret;
}

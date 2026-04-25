package com.academy.scms.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.academy.scms.dto.StudentDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret:your-default-very-long-secret-key-32-chars-min}")
	private String secretKey;

	private static final long EXPIRATION_TIME = 3600000;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(StudentDto student) {

		return Jwts.builder().subject(student.getEmail()).claim("name", student.getName())
				.claim("email", student.getEmail()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(getSigningKey()).compact();
	}

	public String validateTokenAndGetSubject(String token) {
		try {
			return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (Exception e) {
			return null;
		}
	}
}

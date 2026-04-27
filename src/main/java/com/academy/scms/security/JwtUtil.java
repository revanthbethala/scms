package com.academy.scms.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.academy.scms.dto.StudentDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.expire}")
	private  long expiryTime;

	SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(StudentDto student) {
		System.out.println("expire:"+this.expiryTime);
		return Jwts.builder().subject(String.valueOf(student.getId())).claim("name", student.getName())
				.claim("role", student.getRole().name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + this.expiryTime)).signWith(key).compact();
	}

	public String getRoleFromToken(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("role",
					String.class);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public String validateTokenAndGetSubject(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}

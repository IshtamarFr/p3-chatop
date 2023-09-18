package fr.chatop.api.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import fr.chatop.api.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

	private String SECRET_KEY = "B27F48AD27BDAAA197327AA39F2718CABAD2987AB3B3C7";

	public String generateAccessToken(User user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getId(), user.getEmail())).setIssuer("CodeJava")
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

	}
}

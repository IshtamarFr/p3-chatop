package fr.chatop.api.config.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.chatop.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
	private static final long EXPIRE_DURATION = 1 * 24 * 60 * 60 * 1000; // 1 day

	private String SECRET_KEY = "B27F48AD27BDAAA197327AA39F2718CABAD2987AB3B3C7";

	public String generateAccessToken(User user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getId(), user.getEmail())).setIssuer("Ishta")
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

	}

	// Jwt Filtering
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
		}

		return false;
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	public long getIdFromValidToken(String jwt) {
		try {
			String token = jwt.split(" ")[1].trim();
			String[] jwtSubject = getSubject(token).split(",");
			return Long.parseLong(jwtSubject[0]);
		} catch (Exception e) {
			return 0;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}

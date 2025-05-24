package com.api.security.jwt;

import com.api.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtils implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private final String jwtSecret;

	private final Long jwtExpired;

	public JwtTokenUtils(Environment env) {
		this.jwtSecret = env.getProperty("com.api.jwtSecret");
		this.jwtExpired = env.getProperty("com.api.jwtExpirationMs", Long.class);
	}

	/**
	 * Get bearer token from the HttpServletRequest
	 *
	 * @param request current processing HttpServletRequest
	 * @return token
	 */
	public String getTokenFromRequest(HttpServletRequest request) {
		String tokenPrefix = "Bearer ";
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authHeader) && authHeader.startsWith(tokenPrefix)) {
			return authHeader.substring(tokenPrefix.length()).strip();
		}
		return null;
	}

	/**
	 * Generate token using user details
	 *
	 * @param userDetails customized user details
	 * @return token
	 */
	public String generateJwtToken(UserDetailsImpl userDetails) {
		return this.generateJwtToken(userDetails.getUsername(), userDetails);
	}

	/**
	 * Generate token with predefined subject using user details
	 *
	 * @param subject     subject(username)
	 * @param userDetails customized user details
	 * @return token
	 */
	public String generateJwtToken(String subject, UserDetailsImpl userDetails) {
		final Date systemDate = new Date();

		Map<String, Object> dataClaim = new HashMap<>();
		dataClaim.put("user", subject);
		dataClaim.put("authority", userDetails.getAuthorities());
		dataClaim.put("username", userDetails.getUsername());
		dataClaim.put("auth_at", systemDate.getTime());

		return Jwts.builder()
			.setSubject(subject)
			.setClaims(dataClaim)
			.setIssuedAt(systemDate)
			.setExpiration(new Date(systemDate.getTime() + jwtExpired))
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	/**
	 * Get username from token
	 *
	 * @param token token
	 * @return username
	 */
	public String getUserName(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("username", String.class);
	}

	/**
	 * Get token's expired date from token
	 *
	 * @param token token
	 * @return token's expired date
	 */
	public Date getExpiration(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
	}

	/**
	 * Validate token
	 *
	 * @param authToken token
	 * @return valid status
	 */
	public Boolean validate(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		} catch (SignatureException e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}
}

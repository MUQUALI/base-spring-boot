package com.api.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Component
public class CorsConfigurationSourceImpl implements CorsConfigurationSource {
	private final String[] allowedOrigins;
	private final String[] allowedMethods;
	private final String[] allowedHeaders;
	private final long maxAge;

	/**
	 * Autowired constructor
	 *
	 * @param env environment utility class
	 */
	@Autowired
	public CorsConfigurationSourceImpl(Environment env) {
		allowedOrigins = env.getProperty("cors.allowed.origins", String[].class);
		allowedMethods = env.getProperty("cors.allowed.methods", String[].class);
		allowedHeaders = env.getProperty("cors.allowed.headers", String[].class);
		maxAge = env.getProperty("cors.max-age", Long.class, 3600L);
	}

	/**
	 * Override the cors configuration with a new CorsConfiguration
	 * instance, using properties file's variables
	 *
	 * @param request HttpServletRequest
	 * @return CorsConfiguration
	 */
	@Override
	public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
		CorsConfiguration cors = new CorsConfiguration();
		cors.setAllowedOrigins(List.of(allowedOrigins));
		cors.setAllowedMethods(List.of(allowedMethods));
		cors.setAllowedHeaders(List.of(allowedHeaders));
		cors.setMaxAge(maxAge);

		return cors;
	}
}

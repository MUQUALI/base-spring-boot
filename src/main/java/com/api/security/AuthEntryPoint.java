package com.api.security;

import com.api.dto.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
@Configuration
public class AuthEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper mapper;

	@Autowired
	public AuthEntryPoint(ObjectMapper objectMapper) {
		this.mapper = objectMapper;
	}

	/**
	 * Handle exception when filtering request
	 *
	 * @param request       HttpServletRequest
	 * @param response      HttpServletResponse
	 * @param authException exception thrown when processing filter
	 * @throws IOException I/O exception thrown when getting output stream and write response using mapper
	 */
	@Override
	public void commence(HttpServletRequest request,
						 HttpServletResponse response,
						 AuthenticationException authException) throws IOException {
		log.error("Unauthorized error. Message - {} - {} - {}",
				authException.getMessage(), request.getRequestURI(), request.getMethod());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ApiResponse apiResponse = new ApiResponse(response.getStatus(), HttpStatus.UNAUTHORIZED.toString(), null);

		response.getOutputStream().println(mapper.writeValueAsString(apiResponse));
	}
}

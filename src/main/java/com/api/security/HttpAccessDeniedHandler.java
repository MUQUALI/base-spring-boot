package com.api.security;

import com.api.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Date;

@Configuration
public class HttpAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper mapper;

	@Autowired
	public HttpAccessDeniedHandler(ObjectMapper objectMapper) {
		this.mapper = objectMapper;
	}
	
	/**
	 * Handling the denied access exception
	 *
	 * @param request               HttpServletRequest
	 * @param response              HttpServletResponse
	 * @param accessDeniedException exception thrown when the access is denied
	 * @throws IOException I/O exception thrown when getting output stream and write response using mapper
	 */
	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_FORBIDDEN, new Date(),
				"Access Denied. You don't have permission to access this resource.");
		response.setStatus(errorResponse.getInternalStatus());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(mapper.writeValueAsString(errorResponse));
	}
}

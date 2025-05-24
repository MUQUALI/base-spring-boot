package com.api.security.jwt;

import com.api.config.CaffeineConfig;
import com.api.exception.InvalidTokenRequestException;
import com.api.security.userdetails.UserDetailsImpl;
import com.api.security.userdetails.UserDetailsServiceImpl;
import com.api.service.CacheService;
import com.api.utils.ValidationUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@Configuration
public class JwtTokenFilter extends OncePerRequestFilter {
	private final String[] noTokenAPIs;
	private final JwtTokenUtils jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final CacheService cacheService;

	public JwtTokenFilter(JwtTokenUtils jwtUtil, UserDetailsServiceImpl userDetailsService, CacheService cacheService, Environment env) {
		String[] noTokenApiEnv = env.getProperty("security.api.allowed", String[].class);
		if (Objects.isNull(noTokenApiEnv)) {
			this.noTokenAPIs = new String[0];

		} else {
			this.noTokenAPIs = Arrays.stream(noTokenApiEnv).map(this::convertPathToRegex).toArray(String[]::new);
		}
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.cacheService = cacheService;
	}

	/**
	 * Read token from the request header. Then proceed to validate the token using the JwtTokenUtilClass,
	 * if the token is valid, fetch the user account information and set it to the SecurityContextHolder
	 *
	 * @param request     HttpServletRequest
	 * @param response    HttpServletResponse
	 * @param filterChain FilterChain
	 * @throws ServletException Servlet exception thrown by doFilter
	 * @throws IOException      I/O exception thrown by doFilter
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException, InvalidTokenRequestException {

		String requestURI = request.getRequestURI();
		// if the current request API is not allowed to bypass without the token, and the token is valid
		if (Arrays.stream(noTokenAPIs).noneMatch(path -> Pattern.compile(path).matcher(requestURI).matches())) {
			// get token from request
			String token = jwtUtil.getTokenFromRequest(request);
			if (!StringUtils.hasText(token)
				|| Boolean.FALSE.equals(jwtUtil.validate(token))
				|| ValidationUtils.nonNullOrEmpty(cacheService.cacheGet(CaffeineConfig.INVALID_JWT_CACHE, token))) {
				filterChain.doFilter(request, response);
				return;
			}

			// fetch user information
			String username = jwtUtil.getUserName(token);
			UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);

			// setup authentication token value
			var authToken = new UsernamePasswordAuthenticationToken(userDetails, token,
				userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			// set the authentication token to security context holder
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}

	private String convertPathToRegex(String path) {

		return path.replace("**", "(.+)$").replace("/*", "/[^/]+$");
	}
}

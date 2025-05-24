package com.api.security;

import com.api.config.CorsConfigurationSourceImpl;
import com.api.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final Environment env;
	private final AuthEntryPoint authEntryPoint;
	private final JwtTokenFilter jwtTokenFilter;
	private final CorsConfigurationSourceImpl corsConfig;
	private final HttpAccessDeniedHandler accessDeniedHandler;

	/**
	 * Create a PasswordEncoder bean
	 *
	 * @return the specific bean
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Create a AuthenticationManager bean
	 *
	 * @return the specific bean
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * Create a SecurityFilterChain class bean, used for configuring
	 * the filter in security layer
	 *
	 * @return the specific bean
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(cors -> cors.configurationSource(corsConfig))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(env.getProperty("security.api.allowed", String[].class)).permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exceptionHandle -> exceptionHandle
						.authenticationEntryPoint(authEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.httpBasic(Customizer.withDefaults())
				.build();
	}
}

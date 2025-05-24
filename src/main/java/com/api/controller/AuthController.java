package com.api.controller;

import com.api.dto.request.LoginRequestDto;
import com.api.dto.request.ResetPasswordRequestDto;
import com.api.dto.response.ApiResponse;
import com.api.service.AuthService;
import com.api.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(
		@RequestBody(required = false) LoginRequestDto loginRequestDto,
		@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String bearerToken) {
		// validate inputs
		List<String> errorResponse = new ArrayList<>();
		errorResponse.add(ValidationUtils.validateNotNullOrEmpty(loginRequestDto, "loginRequestDto"));
		if (ValidationUtils.nonNullOrEmpty(loginRequestDto)) {
			errorResponse.add(ValidationUtils.validateNotNullOrEmpty(loginRequestDto.getUsername(), "username"));
			errorResponse.add(ValidationUtils.validateNotNullOrEmpty(loginRequestDto.getPassword(), "password"));
		}
		String msgError = String.join(",", errorResponse.stream().filter(ValidationUtils::nonNullOrEmpty).toList());
		if (ValidationUtils.nonNullOrEmpty(msgError)) {
			throw new IllegalArgumentException(msgError);
		}

		return ResponseEntity.ok().body(authService.login(loginRequestDto, bearerToken));
	}

	@GetMapping("/init-reset-password")
	public ResponseEntity<ApiResponse> initResetPassword(@RequestParam(required = false) String username,
														 @RequestParam(required = false) String data,
														 @RequestHeader(required = true) String origin) {
		// validate inputs
		String usernameRequired = ValidationUtils.validateNotNullOrEmpty(username, "username");
		if (ValidationUtils.nonNullOrEmpty(usernameRequired)) {
			throw new IllegalArgumentException(usernameRequired);
		}

		return ResponseEntity.ok().body(authService.initResetPassword(username, data, origin));
	}

	@GetMapping("/validate-reset-token")
	public ResponseEntity<ApiResponse> validateResetToken(@RequestParam(required = false) String token) {
		// validate inputs
		String tokenRequired = ValidationUtils.validateNotNullOrEmpty(token, "token");
		if (ValidationUtils.nonNullOrEmpty(tokenRequired)) {
			throw new IllegalArgumentException(tokenRequired);
		}

		return ResponseEntity.ok().body(authService.validateResetToken(token));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody(required = false) ResetPasswordRequestDto requestDto) {
		// validate inputs
		List<String> errorResponse = new ArrayList<>();
		errorResponse.add(ValidationUtils.validateNotNullOrEmpty(requestDto, "requestDto"));
		if (ValidationUtils.nonNullOrEmpty(requestDto)) {
			errorResponse.add(ValidationUtils.validateNotNullOrEmpty(requestDto.getNewPassword(), "newPassword"));
			errorResponse.add(
				ValidationUtils.validateNotNullOrEmpty(requestDto.getConfirmNewPassword(), "confirmNewPassword"));
			errorResponse.add(ValidationUtils.validateNotNullOrEmpty(requestDto.getResetToken(), "resetToken"));
		}
		String msgError = String.join(",", errorResponse.stream().filter(ValidationUtils::nonNullOrEmpty).toList());
		if (ValidationUtils.nonNullOrEmpty(msgError)) {
			throw new IllegalArgumentException(msgError);
		}

		return ResponseEntity.ok().body(authService.resetPassword(requestDto));
	}

	@PostMapping("/req/logout")
	public ResponseEntity<ApiResponse> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken) {
		return ResponseEntity.ok().body(authService.logout(bearerToken));
	}
}

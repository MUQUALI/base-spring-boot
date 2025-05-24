package com.api.controller;

import com.api.common.OpenAPIConstants;
import com.api.dto.response.ApiResponse;
import com.api.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = OpenAPIConstants.Dashboard.TAG_NAME, description = OpenAPIConstants.Dashboard.DESCRIPTION)
public class DashboardController {
	private final DashboardService dashboardService;

	@GetMapping("/hello-user")
	@Operation(summary = "Test API for dashboard screen, get the user's username by the user ID")
	public ResponseEntity<ApiResponse> getHelloUser() {
		return ResponseEntity.ok().body(ApiResponse.apiResponseSuccess(dashboardService.getHelloUser()));
	}
}

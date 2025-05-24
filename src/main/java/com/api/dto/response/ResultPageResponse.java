package com.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPageResponse {
	private Long totalItems;

	private Integer totalPages;

	private Integer pageSize;

	private Integer currentPage;

	private List<?> items;
}
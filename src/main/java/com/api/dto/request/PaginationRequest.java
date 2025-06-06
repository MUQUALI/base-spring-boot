package com.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationRequest {

	private Integer pageNo;

	private Integer pageSize;
}

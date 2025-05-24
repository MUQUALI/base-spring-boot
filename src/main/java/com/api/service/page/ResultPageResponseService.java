package com.api.service.page;

import com.api.dto.request.PaginationRequest;
import com.api.dto.response.ResultPageResponse;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ResultPageResponseService {
	<T> ResultPageResponse resultPage(PaginationRequest request, Page<?> data, Class<T> responseDTO);

	ResultPageResponse resultPage(PaginationRequest request, Page<?> data);

	<T> ResultPageResponse resultPagination(PaginationRequest request, NativeQuery<?> query, Class<T> clazz,
			int totalRecords);

	ResultPageResponse resultPaginationNotClass(PaginationRequest request, List<?> arrays, int totalRecords);

	ResultPageResponse resultPage(PaginationRequest request, List<?> data);
}

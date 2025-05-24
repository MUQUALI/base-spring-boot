package com.api.service.page;

import com.api.dto.request.PaginationRequest;
import com.api.dto.response.ResultPageResponse;
import com.api.utils.Utils;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultPageResponseServiceImpl implements ResultPageResponseService {

	@Override
	public <T> ResultPageResponse resultPage(PaginationRequest request, Page<?> data, Class<T> responseDTO) {
		ResultPageResponse res = new ResultPageResponse();
		res.setPageSize(request.getPageSize());

		res.setCurrentPage(data.getNumber());
		res.setItems(Utils.resultExtends(responseDTO, data.getContent()));
		res.setTotalPages(data.getTotalPages());
		res.setTotalItems(data.getTotalElements());
		return res;
	}

	public ResultPageResponse resultPage(PaginationRequest request, Page<?> data) {
		ResultPageResponse res = new ResultPageResponse();
		res.setPageSize(request.getPageSize());
		res.setItems(data.getContent());
		res.setTotalPages(data.getTotalPages());
		res.setCurrentPage(data.getNumber());
		res.setTotalItems(data.getTotalElements());
		return res;
	}

	@Override
	public <T> ResultPageResponse resultPagination(PaginationRequest request, NativeQuery<?> query, Class<T> clazz,
												   int totalRecords) {
		ResultPageResponse resultPageResponse = new ResultPageResponse();
		Utils.addScalar(query, clazz);
		int totalPages;
		if (totalRecords % request.getPageSize() == 0) {
			totalPages = totalRecords / request.getPageSize();
		} else {
			totalPages = totalRecords / request.getPageSize() + 1;
		}
		resultPageResponse.setTotalPages(totalPages);
		resultPageResponse.setTotalItems((long) totalRecords);
		resultPageResponse.setPageSize(request.getPageNo());
		resultPageResponse.setCurrentPage(request.getPageSize());
		resultPageResponse.setItems(query.getResultList());
		return resultPageResponse;
	}

	@Override
	public ResultPageResponse resultPaginationNotClass(PaginationRequest request, List<?> arrays, int totalRecords) {
		ResultPageResponse resultPageResponse = new ResultPageResponse();
		int totalPages;
		if (totalRecords % request.getPageSize() == 0) {
			totalPages = totalRecords / request.getPageSize();
		} else {
			totalPages = totalRecords / request.getPageSize() + 1;
		}
		resultPageResponse.setTotalPages(totalPages);
		resultPageResponse.setTotalItems((long) totalRecords);
		resultPageResponse.setPageSize(request.getPageSize());
		resultPageResponse.setCurrentPage(request.getPageNo());
		resultPageResponse.setItems(arrays);
		return resultPageResponse;
	}

	@Override
	public ResultPageResponse resultPage(PaginationRequest request, List<?> data) {
		int pageNo = request.getPageNo();
		int pageSize = request.getPageSize();

		ResultPageResponse resultPageResponse = new ResultPageResponse();
		int totalPages = (int) Math.ceil((float) data.size() / (float) pageSize);
		long totalItems = data.size();
		List<?> pageItems = data.stream().skip(pageNo * (long) pageSize).limit(pageSize).toList();

		resultPageResponse.setTotalPages(totalPages);
		resultPageResponse.setTotalItems(totalItems);
		resultPageResponse.setPageSize(pageSize);
		resultPageResponse.setCurrentPage(pageNo);
		resultPageResponse.setItems(pageItems);
		return resultPageResponse;
	}

}

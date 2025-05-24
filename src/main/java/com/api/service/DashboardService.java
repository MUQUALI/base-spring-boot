package com.api.service;

import com.api.database.entity.UserAccountEntity;
import com.api.database.repository.UserAccountRepository;
import com.api.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
	/** JPA repository of UserAccountEntity */
	private final UserAccountRepository userAccountRepository;

	/** Current request's user info */
	private final RequestInfoService requestInfoService;

	/**
	 * Get username from user ID
	 *
	 * @return username
	 */
	public String getHelloUser() {
		UserAccountEntity userAccount = userAccountRepository.findByIdAndDeleteFlagFalse(requestInfoService.getUserId())
			.orElseThrow(RecordNotFoundException::new);
		return userAccount.getEmail();
	}
}

package com.api.security.userdetails;

import com.api.database.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserAccountRepository userAccountRepository;

	@Override
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		return userAccountRepository.findByUsernameOrEmailAndDeleteFlagFalse(username, username)
				.map(UserDetailsImpl::build).orElseThrow(() -> new UsernameNotFoundException(username));
	}
}

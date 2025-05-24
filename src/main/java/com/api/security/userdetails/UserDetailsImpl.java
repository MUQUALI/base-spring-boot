package com.api.security.userdetails;

import com.api.database.entity.UserAccountEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class UserDetailsImpl implements UserDetails {
	@Serial
	private static final long serialVersionUID = 1L;

	private int id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public static UserDetailsImpl build(UserAccountEntity userAccount) {
		// setup authorities
		List<String> authorities = new ArrayList<>();
		Optional.ofNullable(userAccount.getRoleGroupEntity()).ifPresent(roleGroup -> {
			// TODO: set authorization flags
		});

		return UserDetailsImpl.builder()
				.id(userAccount.getId())
				.username(userAccount.getUsername())
				.password(userAccount.getPassword())
				.authorities(authorities.stream().map(SimpleGrantedAuthority::new).toList())
				.build();
	}
}

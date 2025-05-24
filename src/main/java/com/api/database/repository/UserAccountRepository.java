package com.api.database.repository;

import com.api.database.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

	Optional<UserAccountEntity> findByUsernameOrEmailAndDeleteFlagFalse(String username, String email);

	Optional<UserAccountEntity> findByIdAndDeleteFlagFalse(Integer userAccountId);
}

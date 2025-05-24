package com.api.database.repository;

import com.api.database.entity.ForgotPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordEntity, Integer> {

	Optional<ForgotPasswordEntity> findFirstByUserAccountIdAndAndStatusAndDeleteFlagFalseOrderByCreatedAtDesc(Integer id, Integer status);

	Optional<ForgotPasswordEntity> findByTokenAndStatusAndDeleteFlagFalse(String token, Integer status);
}

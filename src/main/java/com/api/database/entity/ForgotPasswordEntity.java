package com.api.database.entity;

import com.api.common.CommonConstants;
import com.api.database.entity.common.CommonEntity;
import com.api.database.entity.common.CommonEntityConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = CommonEntityConstants.FORGOT_PASSWORD_ENTITY)
@EqualsAndHashCode(callSuper = true)
public class ForgotPasswordEntity extends CommonEntity {

	@Column(name = CommonEntityConstants.USER_ACCOUNT_ID)
	@NotNull
	private Integer userAccountId;

	@Column(name = CommonEntityConstants.EMAIL)
	@NotNull
	@Size(max = CommonConstants.MAX_60, message = CommonConstants.VALIDATION_MAX_SIZE_60)
	private String email;

	@Column(name = CommonEntityConstants.ORIGIN)
	@NotNull
	@Size(max = CommonConstants.MAX_100, message = CommonConstants.VALIDATION_MAX_SIZE_100)
	private String origin;

	@Column(name = CommonEntityConstants.TOKEN)
	@NotNull
	@Size(max = CommonConstants.MAX_2000, message = CommonConstants.VALIDATION_MAX_SIZE_2000)
	private String token;

	@Column(name = CommonEntityConstants.EXPIRED_DATE)
	@NotNull
	private LocalDateTime expiredDate;

	@Column(name = CommonEntityConstants.STATUS)
	@NotNull
	private Integer status;
}

package com.api.database.entity;

import com.api.common.CommonConstants;
import com.api.database.entity.common.CommonEntity;
import com.api.database.entity.common.CommonEntityConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = CommonEntityConstants.USER_ACCOUNT_ENTITY)
public class UserAccountEntity extends CommonEntity {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = CommonEntityConstants.USERNAME)
	@Size(max = CommonConstants.MAX_60, message = CommonConstants.VALIDATION_MAX_SIZE_60)
	@NotNull(message = CommonConstants.VALIDATION_NOT_NULL)
	private String username;

	@Column(name = CommonEntityConstants.PASSWORD)
	@Size(max = CommonConstants.MAX_200, message = CommonConstants.VALIDATION_MAX_SIZE_200)
	@NotNull(message = CommonConstants.VALIDATION_NOT_NULL)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = CommonEntityConstants.ROLE_GROUP_ID, nullable = false)
	@JsonProperty
	private RoleGroupEntity roleGroupEntity;

	@Column(name = CommonEntityConstants.EMAIL)
	@Size(max = CommonConstants.MAX_60, message = CommonConstants.VALIDATION_MAX_SIZE_60)
	@NotNull(message = CommonConstants.VALIDATION_NOT_NULL)
	private String email;

	@Column(name = CommonEntityConstants.PHONE_NUMBER)
	@Size(max = CommonConstants.MAX_10, message = CommonConstants.VALIDATION_MAX_SIZE_10)
	@NotNull(message = CommonConstants.VALIDATION_NOT_NULL)
	private String phoneNumber;

	@Column(name = CommonEntityConstants.START_DATE)
	private LocalDate startDate;

	@Column(name = CommonEntityConstants.RESIGN_DATE)
	private LocalDate resignDate;
}

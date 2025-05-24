package com.api.database.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CommonEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = CommonEntityConstants.ID)
	private Integer id;

	@Column(name = CommonEntityConstants.CREATED_AT, nullable = false)
	private LocalDateTime createdAt;

	@Column(name = CommonEntityConstants.CREATED_BY, nullable = false)
	private Integer createdBy;

	@Column(name = CommonEntityConstants.UPDATED_AT, nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = CommonEntityConstants.UPDATED_BY, nullable = false)
	private Integer updatedBy;

	@Column(name = CommonEntityConstants.DELETE_FLAG, nullable = false)
	private Boolean deleteFlag;

	@Transient
	private Boolean isUpdate = true;

	public void setCommonRegist(Integer userAccountId) {
		this.createdAt = LocalDateTime.now();
		this.createdBy = userAccountId;
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = userAccountId;
		this.deleteFlag = false;
	}

	public void setCommonUpdate(Integer userAccountId) {
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = userAccountId;
	}

	public void setCommonDelete(Integer userAccountId) {
		this.deleteFlag = true;
		this.setCommonUpdate(userAccountId);
	}
}

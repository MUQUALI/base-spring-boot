package com.api.database.entity;

import com.api.database.entity.common.CommonEntity;
import com.api.database.entity.common.CommonEntityConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = CommonEntityConstants.ROLE_GROUP_ENTITY)
public class RoleGroupEntity extends CommonEntity {
	@Serial
	private static final long serialVersionUID = 1L;
}

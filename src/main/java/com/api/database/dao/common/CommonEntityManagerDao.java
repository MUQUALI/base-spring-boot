package com.api.database.dao.common;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

@RequiredArgsConstructor
public class CommonEntityManagerDao extends CommonSearchColumn {
	private EntityManager entityManager;

	public StringBuilder sqlBuilder() {
		return new StringBuilder();
	}

	public Session session() {
		return entityManager.unwrap(Session.class);
	}
}

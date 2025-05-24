package com.api.database.dao;

import com.api.database.dao.common.CommonEntityManagerDao;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAccountDao extends CommonEntityManagerDao {
}

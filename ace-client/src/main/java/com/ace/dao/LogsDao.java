package com.ace.dao;

import com.ace.entities.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Classname: LogsDao
 * @Date: 30/3/2024 6:29 am
 * @Author: garlam
 * @Description:
 */
public interface LogsDao extends JpaRepository<Logs, Long>, JpaSpecificationExecutor<Logs> {
}

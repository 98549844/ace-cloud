package com.ace.service;

import com.ace.dao.LogsDao;
import com.ace.entities.Logs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Classname: LogsService
 * @Date: 30/3/2024 6:30 am
 * @Author: garlam
 * @Description:
 */

@Service
public class LogsService {
    private static final Logger log = LogManager.getLogger(LogsService.class.getName());

    private LogsDao logsDao;

    @Autowired
    public LogsService(LogsDao logsDao) {
        this.logsDao = logsDao;
    }

    public void save(Logs logs) {
        logsDao.save(logs);
    }
}


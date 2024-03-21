package com.ace.service;

import com.ace.dao.UsersDao;
import com.ace.entities.Users;
import com.ace.response.RespData;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @Classname: UsersService
 * @Date: 21/3/2024 6:02 pm
 * @Author: garlam
 * @Description:
 */

@Service
public class UsersService {
    private static final Logger log = LogManager.getLogger(UsersService.class.getName());

    private final UsersDao usersDao;

    @Autowired
    public UsersService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public List<Users> getAll() {
        return usersDao.findAll();
    }

    public Users getByUserAccount(String userAccount) {
        return usersDao.findByUserAccount(userAccount);

    }

    public void saveAll(List<Users> users){
        usersDao.saveAll(users);

    }

    public void save(Users user) {
        usersDao.save(user);
    }

    public void deleteByUserAccount(String userAccount) {
        usersDao.deleteByUserAccount(userAccount);
    }

    public void deleteAll() {
        usersDao.deleteAll();
    }


}


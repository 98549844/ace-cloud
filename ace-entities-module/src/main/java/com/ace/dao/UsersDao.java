package com.ace.dao;

import com.ace.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname: UsersDao
 * @Date: 21/3/2024 6:08 pm
 * @Author: garlam
 * @Description:
 */

@Repository
@Transactional
public interface UsersDao extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Users findByUserAccount(String userAccount);
    void deleteByUserAccount(String userAccount);
}

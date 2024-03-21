package com.ace.service;

import com.ace.entities.Pay;

import java.util.List;


/**
 * @Classname: PayService
 * @Date: 18/3/2024 6:12 am
 * @Author: garlam
 * @Description:
 */

public interface PayService {

    int add(Pay pay);
    int delete(Integer id);
    int update(Pay pay);
    Pay getById(Integer id);
    List<Pay> getAll();

}


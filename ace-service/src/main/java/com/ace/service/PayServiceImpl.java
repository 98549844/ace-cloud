package com.ace.service;

import com.ace.entities.Pay;
import com.ace.mapper.PayMapper;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Classname: PayServiceImpl
 * @Date: 18/3/2024 6:15 am
 * @Author: garlam
 * @Description:
 */

@Service
public class PayServiceImpl implements PayService {
    private static final Logger log = LogManager.getLogger(PayServiceImpl.class.getName());

    @Resource
    private PayMapper payMapper;


    @Override
    public int add(Pay pay)
    {
        return payMapper.insertSelective(pay);
    }

    @Override
    public int delete(Integer id)
    {
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Pay pay)
    {
        return payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public Pay getById(Integer id)
    {
        return payMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pay> getAll()
    {
        return payMapper.selectAll();
    }
}


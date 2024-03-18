package com.ace.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 暴露给前端的数据对像
 * @Classname: PayDto
 * @Date: 18/3/2024 6:08 am
 * @Author: garlam
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDto implements Serializable {
    private static final Logger log = LogManager.getLogger(PayDto.class.getName());

    private Integer id;

    //支付流水号
    private String payNo;
    //订单流水号
    private String orderNo;
    //用户账号ID
    private Integer userId;
    //交易金额
    private BigDecimal amount;

}


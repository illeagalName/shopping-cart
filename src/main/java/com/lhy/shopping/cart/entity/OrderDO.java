/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 刘红月
 * @version Order.java, v 0.1 2021-02-16 11:54 刘红月 Exp $$
 */
@Data
public class OrderDO {
    private Long id; //
    private LocalDateTime orderDate; //                  null comment '下单时间',
    private BigDecimal amount; //                  null comment '金额',
    private Long userId; //                  null,
    private String userAddress; //                  null,
    private LocalDateTime addTime; //CURRENT_TIMESTAMP null,
    private LocalDateTime updateTime; //                  null
}

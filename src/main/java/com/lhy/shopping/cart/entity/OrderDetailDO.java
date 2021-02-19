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
 * @version OrderDetailDO.java, v 0.1 2021-02-16 11:57 刘红月 Exp $$
 */
@Data
public class OrderDetailDO {
    private Long id;//int auto_increment
    private Long orderId;//int                                 null,
    private String productCode;//int                                 null,
    private Integer quantity;//int                                 null comment '数量',
    private BigDecimal price;//decimal(10, 4)                      null comment '单价',
    private BigDecimal amount;//decimal(10, 4)                      null comment '小计',
    private LocalDateTime addTime;//timestamp default CURRENT_TIMESTAMP null,
    private LocalDateTime updateTime;//timestamp                           null
}

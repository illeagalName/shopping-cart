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
 * @version ProductDO.java, v 0.1 2021-02-16 12:02 刘红月 Exp $$
 */
@Data
public class ProductDO {
    private Long id;//int auto_increment
    private String name;//varchar(50)                         null,
    private BigDecimal price;//decimal(10, 4)                      null,
    private String code;
    private String image;//varchar(200)                        null,
    private LocalDateTime addTime;//timestamp default CURRENT_TIMESTAMP null,
    private LocalDateTime updateTime;//timestamp                           null
    private String description;
    private String tags;
}

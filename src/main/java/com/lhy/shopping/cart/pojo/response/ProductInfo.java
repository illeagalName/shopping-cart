/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.pojo.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 刘红月
 * @version ProductInfo.java, v 0.1 2021-02-16 19:00 刘红月 Exp $$
 */
@Data
public class ProductInfo {
    private String name;
    private BigDecimal price;
    private String image;
    private String code;
    private String description;
    private List<String> tags;
}

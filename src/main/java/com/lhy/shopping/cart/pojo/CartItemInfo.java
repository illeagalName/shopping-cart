package com.lhy.shopping.cart.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemInfo {

    private ProductInfo productInfo;
    private int quantity;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return productInfo.getPrice().multiply(new BigDecimal(quantity));
    }

}
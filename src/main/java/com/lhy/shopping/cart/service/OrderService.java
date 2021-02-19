/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service;

import com.lhy.shopping.cart.pojo.CartInfo;

/**
 * @author 杨文栋
 * @version OrderService.java, v 0.1 2021-02-19 17:07 杨文栋 Exp $$
 */
public interface OrderService {
    void saveOrder(CartInfo cartInfo);
}

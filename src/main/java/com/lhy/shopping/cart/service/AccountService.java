/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service;

import com.lhy.shopping.cart.pojo.response.AccountResp;

/**
 * @author 刘红月
 * @version Customer.java, v 0.1 2021-02-13 15:37 刘红月 Exp $$
 */
public interface AccountService {
    /**
     * 根据用户userName查询用户信息
     *
     * @param userName
     * @return
     */
    AccountResp getCustomer(String userName);
}

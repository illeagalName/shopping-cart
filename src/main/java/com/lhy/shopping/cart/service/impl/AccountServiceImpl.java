/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service.impl;

import com.lhy.shopping.cart.dao.AccountDAO;
import com.lhy.shopping.cart.entity.AccountDO;
import com.lhy.shopping.cart.pojo.response.AccountResp;
import com.lhy.shopping.cart.service.AccountService;
import com.lhy.shopping.cart.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘红月
 * @version CustomerServiceImpl.java, v 0.1 2021-02-13 15:39 刘红月 Exp $$
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDAO accountDAO;

    @Override
    public AccountResp getCustomer(String userName) {
        AccountDO accountDO = accountDAO.findAccount(userName);
        AccountResp accountResp = new AccountResp();
        BeanUtils.copy(accountDO, accountResp);
        return accountResp;
    }
}

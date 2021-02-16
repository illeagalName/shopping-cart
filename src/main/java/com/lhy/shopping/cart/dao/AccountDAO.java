/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.dao;

import com.lhy.shopping.cart.entity.AccountDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 刘红月
 * @version CustomerDAO.java, v 0.1 2021-02-13 15:35 刘红月 Exp $$
 */
@Mapper
public interface AccountDAO {
    AccountDO findAccount(@Param("userName") String userName);
}

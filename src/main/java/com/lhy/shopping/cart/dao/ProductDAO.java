/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.dao;

import com.lhy.shopping.cart.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 刘红月
 * @version CustomerDAO.java, v 0.1 2021-02-13 15:35 刘红月 Exp $$
 */
@Mapper
public interface ProductDAO {
    List<ProductDO> listProduct(@Param("likeName") String likeName);
    ProductDO findProduct(@Param("code") String code);
}

/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service;

import com.lhy.shopping.cart.pojo.ListResult;
import com.lhy.shopping.cart.pojo.response.ProductInfo;

/**
 * @author 刘红月
 * @version ProductService.java, v 0.1 2021-02-16 18:58 刘红月 Exp $$
 */
public interface ProductService {
    ListResult<ProductInfo> listProduct(int page, int pageSize,String likeName);
}

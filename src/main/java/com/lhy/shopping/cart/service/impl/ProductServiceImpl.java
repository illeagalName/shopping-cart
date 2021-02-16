/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhy.shopping.cart.dao.ProductDAO;
import com.lhy.shopping.cart.entity.ProductDO;
import com.lhy.shopping.cart.pojo.ListResult;
import com.lhy.shopping.cart.pojo.response.ProductInfo;
import com.lhy.shopping.cart.service.ProductService;
import com.lhy.shopping.cart.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 刘红月
 * @version ProductServiceImpl.java, v 0.1 2021-02-16 19:04 刘红月 Exp $$
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public ListResult<ProductInfo> listProduct(int page, int pageSize, String likeName) {
        Page<List<ProductDO>> objects = PageHelper.startPage(page, pageSize);
        List<ProductDO> productDOS = productDAO.listProduct(likeName);
        List<ProductInfo> productInfos = productDOS.stream().map(productDO -> {
            ProductInfo pInfo = new ProductInfo();
            BeanUtils.copy(productDO, pInfo);
            if (Objects.nonNull(productDO.getTags())) {
                pInfo.setTags(Arrays.asList(productDO.getTags().split(",")));
            }
            return pInfo;
        }).collect(Collectors.toList());
        return new ListResult<>(productInfos, objects);
    }
}

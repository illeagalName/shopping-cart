/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import com.lhy.shopping.cart.pojo.ListResult;
import com.lhy.shopping.cart.pojo.response.ProductInfo;
import com.lhy.shopping.cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 刘红月
 * @version ProductController.java, v 0.1 2021-02-16 18:23 刘红月 Exp $$
 */
@Controller
public class ProductController extends CommonController {

    @Autowired
    ProductService productService;

    @GetMapping({"/productList"})
    public String listProductHandler(Model model, @RequestParam(value = "name",defaultValue = "") String likeName) {

        ListResult<ProductInfo> productInfoListResult = productService.listProduct(page, pageSize, likeName);

        model.addAttribute("paginationProducts", productInfoListResult);
        model.addAttribute("selected",1);
        return "productList";
    }

}

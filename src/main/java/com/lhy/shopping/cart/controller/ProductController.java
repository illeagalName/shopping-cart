/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.pojo.ListResult;
import com.lhy.shopping.cart.pojo.ProductInfo;
import com.lhy.shopping.cart.service.ProductService;
import com.lhy.shopping.cart.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 刘红月
 * @version ProductController.java, v 0.1 2021-02-16 18:23 刘红月 Exp $$
 */
@Controller
public class ProductController extends CommonController {

    @Autowired
    ProductService productService;

    @GetMapping("/productList")
    public String listProduct(Model model, @RequestParam(value = "name", defaultValue = "") String likeName) {

        ListResult<ProductInfo> productInfoListResult = productService.listProduct(page, pageSize, likeName);

        model.addAttribute("paginationProducts", productInfoListResult);
        model.addAttribute("selected", 1);
        return "productList";
    }

    @RequestMapping("/buyProduct")
    public String buyProduct(Model model, @RequestParam(value = "code", defaultValue = "") String code, @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ProductInfo product = null;
        if (code != null && code.length() > 0) {
            product = productService.findProduct(code);
        }
        if (product != null) {
            CartInfo cartInfo = WebUtils.getCartInSession(session);
            cartInfo.addProduct(product, quantity);
        }
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        CartInfo myCart = WebUtils.getCartInSession(session);
        CartInfo cartInfo = WebUtils.getCartInSession(session);

        model.addAttribute("cartForm", myCart);
        model.addAttribute("myCart", cartInfo);
        return "shoppingCart";
    }

}

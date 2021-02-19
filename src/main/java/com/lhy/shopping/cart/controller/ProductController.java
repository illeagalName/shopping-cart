/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import com.lhy.shopping.cart.component.CustomerInfoValidator;
import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.pojo.CustomerInfo;
import com.lhy.shopping.cart.pojo.ListResult;
import com.lhy.shopping.cart.pojo.ProductInfo;
import com.lhy.shopping.cart.service.ProductService;
import com.lhy.shopping.cart.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 刘红月
 * @version ProductController.java, v 0.1 2021-02-16 18:23 刘红月 Exp $$
 */
@Controller
@Slf4j
public class ProductController extends CommonController {

    @Autowired
    ProductService productService;

    @Autowired
    private CustomerInfoValidator customerInfoValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == CustomerInfo.class) {
            dataBinder.setValidator(customerInfoValidator);
        }

    }


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
            CartInfo cartInfo = WebUtils.getCartInCache(request);
            cartInfo.addProduct(product, quantity);
        }
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        CartInfo myCart = WebUtils.getCartInCache(request);
        model.addAttribute("myCart", myCart);
        model.addAttribute("selected", 2);
        return "shoppingCart";
    }

    @GetMapping({"/removeProduct"})
    public String removeProductHandler(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductInfo product = null;
        if (code != null && code.length() > 0) {
            product = productService.findProduct(code);
        }
        if (product != null) {
            CartInfo cartInfo = WebUtils.getCartInCache(request);
            cartInfo.removeProduct(product);
        }
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerInfo(Model model) {
        CartInfo cartInfo = WebUtils.getCartInCache(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (Objects.isNull(customerInfo)) {
            // 从cookies中取出客户信息
            customerInfo = WebUtils.getCustomerInCookies(request);
        }

        model.addAttribute("customerForm", Optional.ofNullable(customerInfo).orElseGet(CustomerInfo::new));
        model.addAttribute("selected", 2);
        return "shoppingCartCustomer";
    }

    @PostMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerSave(Model model, @ModelAttribute("customerInfo") @Validated CustomerInfo customerInfo, BindingResult result) {

        if (result.hasErrors()) {
            customerInfo.setValid(false);
            return "shoppingCartCustomer";
        }

        customerInfo.setValid(true);
        CartInfo cartInfo = WebUtils.getCartInCache(request);
        cartInfo.setCustomerInfo(customerInfo);

        WebUtils.setCustomerInCookies(response, customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    @GetMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationReview(Model model) {
        CartInfo cartInfo = WebUtils.getCartInCache(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("myCart", cartInfo);
        model.addAttribute("selected", 2);
        return "shoppingCartConfirmation";
    }

}

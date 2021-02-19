/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.service.OrderService;
import com.lhy.shopping.cart.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨文栋
 * @version OrderController.java, v 0.1 2021-02-19 17:30 杨文栋 Exp $$
 */
@Controller
@Slf4j
public class OrderController extends CommonController {


    @Autowired
    OrderService orderService;

    @PostMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = WebUtils.getCartInCache(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderService.saveOrder(cartInfo);
        } catch (Exception e) {
            return "shoppingCartConfirmation";
        }

        // 从session中删除cart信息
        WebUtils.removeCartInCache(request);
        // 保存订单信息到session
        WebUtils.storeLastOrderedCartInCache(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @GetMapping("/shoppingCartFinalize")
    public String shoppingCartFinalize(Model model) {

        CartInfo lastOrderedCart = WebUtils.getLastOrderedCartInCache(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }
}

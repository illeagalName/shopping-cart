/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.util;

import com.lhy.shopping.cart.pojo.CartInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 刘红月
 * @version WebUtils.java, v 0.1 2021-02-17 21:19 刘红月 Exp $$
 */
public class WebUtils {
    public static CartInfo getCartInCache(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CartInfo cartInfo = (CartInfo) session.getAttribute("myCart");
        if (cartInfo == null) {
            cartInfo = new CartInfo();
            session.setAttribute("myCart", cartInfo);
        }
        return cartInfo;
    }
}

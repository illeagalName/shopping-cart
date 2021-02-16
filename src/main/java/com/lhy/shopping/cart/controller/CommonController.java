/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 刘红月
 * @version CommonController.java, v 0.1 2021-02-16 18:09 刘红月 Exp $$
 */
public class CommonController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected int page;
    protected int pageSize;

    @ModelAttribute
    public void pretreatment(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "2") int pageSize) {
        this.response = response;
        this.request = request;
        this.session = request.getSession();
        this.page = page;
        this.pageSize = pageSize;
    }
}

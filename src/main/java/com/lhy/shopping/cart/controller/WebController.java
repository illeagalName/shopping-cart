/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 刘红月
 * 所有页面跳转的控制器
 * @version WebController.java, v 0.1 2021-02-15 12:30 刘红月 Exp $$
 */
@Controller
public class WebController {

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("selected",0);
        return "index";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "/403";
    }
}

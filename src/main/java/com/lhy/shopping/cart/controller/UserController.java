/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 杨文栋
 * @version UserController.java, v 0.1 2021-02-22 23:27 杨文栋 Exp $$
 */
@Controller
@Slf4j
public class UserController extends CommonController {

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping( "accountInfo" )
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @GetMapping( "logout" )
    public String logout(Model model) {
//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "index";
    }
}

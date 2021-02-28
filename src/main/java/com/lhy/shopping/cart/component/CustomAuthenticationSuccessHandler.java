/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.component;

import com.alibaba.fastjson.JSON;
import com.lhy.shopping.cart.dao.AccountDAO;
import com.lhy.shopping.cart.entity.AccountDO;
import com.lhy.shopping.cart.pojo.UserVO;
import com.lhy.shopping.cart.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 杨文栋
 * @version CustomAuthenticationSuccessHandler.java, v 0.1 2021-02-28 15:27 杨文栋 Exp $$
 */
@Component("customAuthenticationSuccessHandler")
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    AccountDAO accountDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails credentials = (UserDetails) authentication.getPrincipal();
        AccountDO account = accountDAO.findAccount(credentials.getUsername());
        UserVO user = new UserVO();
        BeanUtils.copy(account, user);
        String s = JSON.toJSONString(user);
        Base64.Encoder encoder = Base64.getEncoder();
        Cookie cookie = new Cookie("user", encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8)));
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.util;

import com.alibaba.fastjson.JSON;
import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.pojo.CustomerInfo;
import com.lhy.shopping.cart.pojo.UserVO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘红月
 * @version WebUtils.java, v 0.1 2021-02-17 21:19 刘红月 Exp $$
 */
@Component
public class WebUtils {

    private static RedissonClient redissonClient;

    public WebUtils(RedissonClient redissonClient) {
        WebUtils.redissonClient = redissonClient;
    }

    public static CartInfo getCartInCache(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        CartInfo cartInfo = (CartInfo) session.getAttribute("myCart");
//        if (cartInfo == null) {
//            cartInfo = new CartInfo();
//            session.setAttribute("myCart", cartInfo);
//        }
        CartInfo cartInfo = new CartInfo();
        UserVO user = getUserInCookies(request);
        if (Objects.nonNull(user)) {
            RBucket<CartInfo> bucket = redissonClient.getBucket(user.getId() + ":myCart");
            if (bucket.isExists()) {
                cartInfo = bucket.get();
            } else {
                bucket.set(cartInfo, 30, TimeUnit.DAYS);
            }
        }
        return cartInfo;
    }

    public static void setCartInCache(HttpServletRequest request, CartInfo cartInfo) {
        UserVO customerInCookies = getUserInCookies(request);
        RBucket<CartInfo> bucket = redissonClient.getBucket(customerInCookies.getId() + ":myCart");
        bucket.set(cartInfo, 30, TimeUnit.DAYS);
    }

    public static UserVO getUserInCookies(HttpServletRequest request) {
        Base64.Decoder decoder = Base64.getDecoder();
        return Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), "user")).map(Cookie::getValue).map(decoder::decode).map(bytes -> new String(bytes, StandardCharsets.UTF_8)).findFirst().map(s -> JSON.parseObject(s, UserVO.class)).orElse(null);
    }

    public static void removeCartInCache(HttpServletRequest request) {
        UserVO customerInCookies = getUserInCookies(request);
        RBucket<CartInfo> bucket = redissonClient.getBucket(customerInCookies.getId() + ":myCart");
        bucket.delete();
    }

    public static void storeLastOrderedCartInCache(HttpServletRequest request, CartInfo cartInfo) {
        request.getSession().setAttribute("lastOrderedCart", cartInfo);
    }

    public static CartInfo getLastOrderedCartInCache(HttpServletRequest request) {
        return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
    }

    public static CustomerInfo getCustomerInCookies(HttpServletRequest request) {
        Base64.Decoder decoder = Base64.getDecoder();
        return Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), "customer")).map(Cookie::getValue).map(decoder::decode).map(bytes -> new String(bytes, StandardCharsets.UTF_8)).findFirst().map(s -> JSON.parseObject(s, CustomerInfo.class)).orElse(null);
    }

    public static void setCustomerInCookies(HttpServletResponse response, CustomerInfo customerInfo) {
        String s = JSON.toJSONString(customerInfo);
        Base64.Encoder encoder = Base64.getEncoder();
        Cookie cookie = new Cookie("customer", encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8)));
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}

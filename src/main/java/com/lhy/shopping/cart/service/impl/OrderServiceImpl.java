/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.lhy.shopping.cart.component.SnowflakeIdWorker;
import com.lhy.shopping.cart.dao.OrderDAO;
import com.lhy.shopping.cart.entity.OrderDO;
import com.lhy.shopping.cart.entity.OrderDetailDO;
import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.pojo.CartItemInfo;
import com.lhy.shopping.cart.pojo.CustomerInfo;
import com.lhy.shopping.cart.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 杨文栋
 * @version OrderServiceImpl.java, v 0.1 2021-02-19 17:09 杨文栋 Exp $$
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    SnowflakeIdWorker idWorker;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    @Transactional
    public void saveOrder(CartInfo cartInfo) {
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        OrderDO orderDO = new OrderDO();
        orderDO.setName(customerInfo.getName());
        orderDO.setPhone(customerInfo.getPhone());
        orderDO.setEmail(customerInfo.getEmail());
        orderDO.setAddress(customerInfo.getAddress());
        orderDO.setOrderDate(LocalDateTime.now());
        orderDO.setOrderNum(idWorker.nextId());
        orderDO.setAmount(cartInfo.getAmountTotal());
        orderDO.setUserId(Optional.ofNullable(cartInfo.getCustomerInfo().getUserId()).orElse(0L));
        orderDAO.saveOrder(orderDO);

        List<CartItemInfo> cartItems = cartInfo.getCartItems();

        List<OrderDetailDO> detailDOS = cartItems.stream().map(item -> {
            OrderDetailDO orderDetailDO = new OrderDetailDO();
            orderDetailDO.setOrderId(orderDO.getId());
            orderDetailDO.setProductCode(item.getProductInfo().getCode());
            orderDetailDO.setQuantity(item.getQuantity());
            orderDetailDO.setPrice(item.getProductInfo().getPrice());
            orderDetailDO.setAmount(item.getAmount());
            return orderDetailDO;
        }).collect(Collectors.toList());
        orderDAO.saveOrderDetail(detailDOS);
        cartInfo.setOrderNum(orderDO.getOrderNum());

        // 异步发送邮件
        CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("2696522060@qq.com");
                simpleMailMessage.setTo(cartInfo.getCustomerInfo().getEmail());
                simpleMailMessage.setSubject("订单通知");
                StringBuilder sb = new StringBuilder("订单信息 ： ");
                sb.append("\n");
                cartItems.forEach(cartItemInfo -> {
                    sb.append("商品名：").append(cartItemInfo.getProductInfo().getName()).append(", 数量：").append(cartItemInfo.getQuantity()).append(", 单价：$").append(cartItemInfo.getProductInfo().getPrice()).append("\n");
                });
                simpleMailMessage.setText(sb.toString());
                if (log.isDebugEnabled()) {
                    log.debug("发送简单邮件{}, {}", JSON.toJSONString(simpleMailMessage), LocalDateTime.now());
                }
                javaMailSender.send(simpleMailMessage);
            } catch (Exception e) {
                log.error("发送简单邮件异常", e);
            }
        });
    }

}

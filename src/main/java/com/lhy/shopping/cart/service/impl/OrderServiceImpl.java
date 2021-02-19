/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.service.impl;

import com.lhy.shopping.cart.component.SnowflakeIdWorker;
import com.lhy.shopping.cart.dao.OrderDAO;
import com.lhy.shopping.cart.entity.OrderDO;
import com.lhy.shopping.cart.entity.OrderDetailDO;
import com.lhy.shopping.cart.pojo.CartInfo;
import com.lhy.shopping.cart.pojo.CartItemInfo;
import com.lhy.shopping.cart.pojo.CustomerInfo;
import com.lhy.shopping.cart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 杨文栋
 * @version OrderServiceImpl.java, v 0.1 2021-02-19 17:09 杨文栋 Exp $$
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    SnowflakeIdWorker idWorker;

    @Autowired
    OrderDAO orderDAO;

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
    }

}

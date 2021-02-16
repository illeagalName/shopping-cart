package com.lhy.shopping.cart.controller.customer;

import com.alibaba.fastjson.JSON;
import com.lhy.shopping.cart.pojo.response.AccountResp;
import com.lhy.shopping.cart.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class CustomerControllerTest {


    @Autowired
    AccountService customerService;

    @Test
    public void m1() {
        AccountResp customer = customerService.getCustomer("zhangsan123");
        System.out.println(JSON.toJSONString(customer));
    }
}
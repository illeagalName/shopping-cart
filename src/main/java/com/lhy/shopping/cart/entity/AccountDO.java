/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘红月
 * @version Customer.java, v 0.1 2021-02-13 15:29 刘红月 Exp $$
 */
@Data
public class AccountDO {
    private Long id;
    private String userName; //用户姓名',
    private String nickName;//'昵称',
    private String password;//'密码',
    private String salt;//'盐',
    private String email;//'邮箱',
    private String phone;//'手机号',
    private Boolean active;
    private String userRole;
    private LocalDateTime addTime; //'添加时间',
    private LocalDateTime updateTime; //'更新时间'
}

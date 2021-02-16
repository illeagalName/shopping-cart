/**
 * Cider
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.lhy.shopping.cart.pojo.response;

import lombok.Data;

/**
 * @author 刘红月
 * @version Customer.java, v 0.1 2021-02-13 15:41 刘红月 Exp $$
 */
@Data
public class AccountResp {
    private Long id;
    private String nickName;
    private String userName;
    private String email;
    private String phone;
}

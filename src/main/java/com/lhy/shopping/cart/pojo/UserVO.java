package com.lhy.shopping.cart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
    private Long id;
    private String userName; //用户姓名',
    private String nickName;//'昵称',
    private String email;//'邮箱',
    private String phone;//'手机号',
}
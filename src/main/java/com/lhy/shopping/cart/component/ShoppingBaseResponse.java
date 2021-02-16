package com.lhy.shopping.cart.component;

import lombok.Data;

import java.io.Serializable;

/**
 * base response
 */
@Data
public class ShoppingBaseResponse implements Serializable {

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应描述
     */
    private String msg;

    /**
     * 响应业务数据
     */
    private Object data;

}

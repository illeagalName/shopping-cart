package com.lhy.shopping.cart.pojo;

import lombok.Data;

@Data
public class CustomerInfo {
	 
    private String name;
    private String address;
    private String email;
    private String phone;
 
    private boolean valid;

}

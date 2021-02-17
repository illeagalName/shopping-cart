package com.lhy.shopping.cart.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartInfo {

    private CustomerInfo customerInfo;

    private final List<CartItemInfo> cartItems = new ArrayList<>();

    public CartInfo() {

    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<CartItemInfo> getCartItems() {
        return this.cartItems;
    }

    private CartItemInfo findItemByCode(String code) {
        return cartItems.stream().filter(item -> Objects.equals(item.getProductInfo().getCode(), code)).findFirst().orElse(null);
    }

    public void addProduct(ProductInfo productInfo, int quantity) {
        CartItemInfo item = this.findItemByCode(productInfo.getCode());

        if (item == null) {
            item = new CartItemInfo();
            item.setQuantity(0);
            item.setProductInfo(productInfo);
            this.cartItems.add(item);
        }
        int newQuantity = item.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartItems.remove(item);
        } else {
            item.setQuantity(newQuantity);
        }
    }


    public void updateProduct(String code, int quantity) {
        CartItemInfo item = this.findItemByCode(code);

        if (item != null) {
            if (quantity <= 0) {
                this.cartItems.remove(item);
            } else {
                item.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(ProductInfo productInfo) {
        CartItemInfo item = this.findItemByCode(productInfo.getCode());
        if (item != null) {
            this.cartItems.remove(item);
        }
    }

    public boolean isEmpty() {
        return this.cartItems.isEmpty();
    }

    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }

    public int getQuantityTotal() {
        return this.cartItems.stream().mapToInt(CartItemInfo::getQuantity).sum();
    }

    public BigDecimal getAmountTotal() {
        return this.cartItems.stream().map(CartItemInfo::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    public void updateQuantity(CartInfo cartForm) {
//        if (cartForm != null) {
//            List<CartItemInfo> lines = cartForm.getCartLines();
//            for (CartItemInfo line : lines) {
//                this.updateProduct(line.getProductInfo().getCode(), line.getQuantity());
//            }
//        }
//
//    }

}
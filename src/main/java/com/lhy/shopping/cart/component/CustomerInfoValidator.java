package com.lhy.shopping.cart.component;

import com.lhy.shopping.cart.pojo.CustomerInfo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerInfoValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == CustomerInfo.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerInfo custInfo = (CustomerInfo) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "收货人名称不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "收货人邮箱不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "收货人地址不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "收货人联系方式不能为空");

        if (!emailValidator.isValid(custInfo.getEmail())) {
            errors.rejectValue("email", "收货人邮箱格式不正确");
        }
    }

}

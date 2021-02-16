package com.lhy.shopping.cart.annotation;


import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.annotation.*;

/**
 * response
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShoppingResponseBody {

    /**
     * serial config
     *
     * @return
     */
    SerializerFeature[] serializerFeature() default {};

}

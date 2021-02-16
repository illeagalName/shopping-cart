package com.lhy.shopping.cart.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lhy.shopping.cart.annotation.ShoppingResponseBody;
import com.lhy.shopping.cart.component.ShoppingBaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * handler response value of all http api
 */
public class ShoppingResponseBodyHandleReturnValue implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {
    /**
     * 处理所有非异常的错误
     *
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        //如果已经是基础的返回值
        return returnType.getParameterType() != ShoppingBaseResponse.class
                && Objects.nonNull(returnType.getAnnotatedElement().getAnnotation(ShoppingResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        assert response != null;
        response.setContentType("application/json;charset=utf-8");
        ShoppingBaseResponse baseResponse = new ShoppingBaseResponse();
        baseResponse.setCode("SUCCESS");
        baseResponse.setMsg("success");
        baseResponse.setData(returnValue);

        ShoppingResponseBody responseBody = returnType.getAnnotatedElement().getAnnotation(ShoppingResponseBody.class);

        SerializerFeature[] defaultSerializerFeature = {
                SerializerFeature.DisableCircularReferenceDetect
        };
        /**
         * 替换注解中的序列化格式
         */
        if (Objects.nonNull(responseBody)) {
            defaultSerializerFeature = responseBody.serializerFeature();
        }
        response.getWriter().write(JSON.toJSONString(baseResponse, defaultSerializerFeature));

    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return supportsReturnType(returnType);
    }
}

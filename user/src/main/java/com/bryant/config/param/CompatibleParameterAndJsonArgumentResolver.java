//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bryant.config.param;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.security.auth.login.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CompatibleParameterAndJsonArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(CompatibleParameterAndJsonArgumentResolver.class);
    private static final DefaultConversionService conversionService = new DefaultConversionService();
    private static Configuration JACKSON_CONFIGURATION;
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";
    private static final String REQUEST_FORM_DATA_BODY = "REQUEST_FORM_DATA_BODY";

    public CompatibleParameterAndJsonArgumentResolver() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 判断是否需要支持该参数
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mvc, NativeWebRequest request, WebDataBinderFactory binder) throws Exception {
        Object parameterValue = request.getParameter(parameter.getParameterName());
        return conversionService.convert(parameterValue, new TypeDescriptor(parameter));
    }




}

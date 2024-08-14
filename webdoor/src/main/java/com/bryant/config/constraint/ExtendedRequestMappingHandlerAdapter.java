package com.bryant.config.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

// Fix Security For JDK9+
// https://spring.io/blog/2022/03/31/spring-framework-rce-early-announcement#suggested-workarounds
public class ExtendedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    @Override
    protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> methods) {
        return new ServletRequestDataBinderFactory(methods, getWebBindingInitializer()) {
            @Override
            protected ServletRequestDataBinder createBinderInstance(
                    Object target, String name, NativeWebRequest request) throws Exception {

                ServletRequestDataBinder binder = super.createBinderInstance(target, name, request);
                String[] fields = binder.getDisallowedFields();
                List<String> fieldList = new ArrayList<>(
                        fields != null ? Arrays.asList(fields) : Collections.emptyList());
                fieldList.addAll(Arrays.asList("class.*", "Class.*", "*.class.*", "*.Class.*"));
                binder.setDisallowedFields(fieldList.toArray(new String[]{}));
                return binder;
            }
        };
    }
}

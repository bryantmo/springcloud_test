package com.bryant.config.mysql;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

public class MybatisSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}

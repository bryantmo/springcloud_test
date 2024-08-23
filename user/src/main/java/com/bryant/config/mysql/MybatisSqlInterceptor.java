package com.bryant.config.mysql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 拦截器做了2个事情
 * - 1.打印原sql
 * - 2.注入自定义参数
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "insert", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "delete", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "get", args =
                {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("MybatisSqlInterceptor interceptor start...");
        Object proceed = invocation.proceed();

        // 1.打印原sql
        log.info("MybatisSqlInterceptor original sql: {}", invocation.getArgs()[0]);

        // 2.注入自定义参数
        Object[] args = invocation.getArgs();

        log.info("MybatisSqlInterceptor interceptor end...");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Interceptor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

}

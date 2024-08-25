package com.bryant.config.mysql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
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
 *
 * Mybatis 插件的执行顺序有两种：
 * 1、不同拦截对象执行顺序，如下：
 * 	Executor` -> `StatementHandler` -> `ParameterHandler` -> `ResultSetHandler`
 * 2、拦截相同对象执行顺序，取决于 mybatis-config.xml 中 <plugin> 配置顺序，越靠后，优先级越高。
 * 3、拦截相同对象执行顺序，如果是使用的配置类加载，则取决于配置类的加载顺序，加载顺序，越靠后，优先级越高；
 */
@Slf4j
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update", args ={MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args =
                {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args =
                {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class TenantIdInjectInterceptor implements Interceptor {

    //用于匹配字符串中的tenant_id关键字，后面可以跟任意数量的空格，然后是等号=，再后面可以跟任意数量的空格，最后是一个问号?。
    // 这个正则表达式使用了Pattern.CASE_INSENSITIVE标志，表示匹配时不区分大小写。
    // 匹配问号，因为问号在正则表达式中有特殊含义（表示前面的字符出现0次或1次），所以需要用两个反斜杠进行转义
    private static final Pattern p = Pattern.compile("tenant_id(\\s+)?=(\\s+)?\\?", Pattern.CASE_INSENSITIVE);
    private static final String SQL_IGNORED_CHARACTOR = "[\\t\\n\\r]";
    /**
     * 核心逻辑在intercept方法，内部实现 sql 获取，参数解析，耗时统计
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("TenantIdInjectInterceptor interceptor start...");

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String namespace = mappedStatement.getId();
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        String methodName = mappedStatement.getId()
                .substring(mappedStatement.getId().lastIndexOf(".") + 1);
        Class<?> c = Class.forName(className);

        //如果Class有注解指明要忽略本次拦截，则放弃拦截
        if (c.isAnnotationPresent(TenantIdInjectConfig.class) && c.getAnnotation(TenantIdInjectConfig.class).ignore()) {
            return invocation.proceed();
        }

        Method[] ms = c.getMethods();
        Method method = Arrays.stream(ms).filter(m -> m.getName().equals(methodName)).findAny().get();
        //如果method 有注解指明要忽略本次拦截，则放弃拦截
        if (method.isAnnotationPresent(TenantIdInjectConfig.class) && method.getAnnotation(TenantIdInjectConfig.class)
                .ignore()) {
            return invocation.proceed();
        }

        //判断SQL中是否存在tenant_id 字段，如果存在，认为已经考虑多租户的情况，否则将SQL拦截下来
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(args[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll(SQL_IGNORED_CHARACTOR, " ");
        boolean sqlWithTenantIdParam;
        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            //insert语句只判断是否有tenant_id
            sqlWithTenantIdParam = sql.contains("tenant_id");
        } else {
            //其他语句判断是否有tenant_id=?
            sqlWithTenantIdParam = p.matcher(sql).find();
        }

        if (!sqlWithTenantIdParam) {
            log.error("缺少对多租户的支持，tenant_id 字段缺失,sql:{}", sql);
            throw new RuntimeException("缺少对多租户的支持，tenant_id字段缺失");
        }

        //这里使用默认的租户id=1
        String defaultTenantId = "1";
        Map map;
        if (args[1] == null) {
            //没有参数列表
            map = new MapperMethod.ParamMap<>();
        } else if (!(args[1] instanceof MapperMethod.ParamMap)) {
            //单参数
            Map tempMap = new MapperMethod.ParamMap<>();
            Parameter[] parameters = method.getParameters();
            Parameter param = parameters[0];
            //第一个参数获取@Param注解，然后获取值
            if (param.isAnnotationPresent(Param.class)) {
                String paramName = param.getAnnotation(Param.class).value();
                tempMap.put(paramName, args[1]);
            } else if (checkTypeType(param)) {
                //如果是基础类型抛出异常
                tempMap.put(param.getName(), args[1]);
            } else {
                //如果没有指定@Param,将单参数的属性单独拎出来处理
                Object arg = args[1];
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        tempMap.put(field.getName(), field.get(arg));
                    }
                }
            }
            args[1] = tempMap;
        }

        //如果是多参数列表时直接转map即可
        map = (Map) args[1];
        if (!StringUtils.isBlank(defaultTenantId)) {
            map.put("tenantId", Long.parseLong(defaultTenantId));
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
//        if (target instanceof Interceptor) {
//            return Plugin.wrap(target, this);
//        } else {
//            return target;
//        }
    }

    private boolean checkTypeType(Object object) {
        if (object.getClass().isPrimitive()
                || object instanceof String
                || object instanceof Boolean
                || object instanceof Double
                || object instanceof Float
                || object instanceof Long
                || object instanceof Integer
                || object instanceof Byte
                || object instanceof Short) {
            return true;
        } else {
            return false;
        }
    }
}

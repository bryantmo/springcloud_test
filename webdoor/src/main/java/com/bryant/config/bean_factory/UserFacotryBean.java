package com.bryant.config.bean_factory;

import com.bryant.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

@Slf4j
public class UserFacotryBean implements FactoryBean<User> {

    /**
     * Spring容器会调用这个方法（实现了FactoryBean接口的工厂类），通过getObject去创建bean
     * @return
     * @throws Exception
     */
    @Override
    public User getObject() throws Exception {
        log.info("UserFacotryBean getObject, new User");
        return new User(1, "user-factory-create-bean");
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
